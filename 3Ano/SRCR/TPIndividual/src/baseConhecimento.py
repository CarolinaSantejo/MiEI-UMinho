import networkx as nx
import matplotlib.pyplot as plt
import re
from math import sin, cos, sqrt, atan2, radians
import csv

def distancia(latitude1, longitude1, latitude2, longitude2):

    R = 6373.0

    lat1 = radians(latitude1)
    lon1 = radians(longitude1)
    lat2 = radians(latitude2)
    lon2 = radians(longitude2)

    dlon = lon2 - lon1
    dlat = lat2 - lat1

    a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))

    distance = R * c
    return distance*1000



pontosRecolha = {}
contentores = []
ruas = {}
ruasAdjacencias = {}

with open('datasetCSV.csv', 'r', encoding='utf-8') as file:
    reader = csv.reader(file, delimiter = ';')
    next(reader)
    for row in reader:
        latitude = row[0]
        longitude = row[1]
        contentorID = row[2]
        pontoRecolha = re.search(r'(\d+): ([\w -]+)( \(|,).+', row[3])
        adjacencias = re.search(r'\([^:]+: ([\w ]+) - ([\w ]+)', row[3])
        pontoRecolhaID = pontoRecolha.group(1)
        ruaNome = pontoRecolha.group(2)
        tipoResiduo = row[4]
        capacidade = row[5]
        pR = (pontoRecolhaID,latitude,longitude,ruaNome)
        contentor = (contentorID,tipoResiduo,capacidade,pontoRecolhaID)
        pontosRecolha[pontoRecolhaID] = pR
        contentores.append(contentor)
        if ruaNome in ruas:
            (ruaNome,listaPR) = ruas[ruaNome]
            listaPR.add(pontoRecolhaID)
            ruas[ruaNome] = (ruaNome,listaPR)
        else:
            ruas[ruaNome] = (ruaNome,set([pontoRecolhaID]))
        if adjacencias:
            if ruaNome in ruasAdjacencias:
                listaR = ruasAdjacencias[ruaNome]
                listaR.add(adjacencias.group(1))
                listaR.add(adjacencias.group(2))
                ruasAdjacencias[ruaNome] = listaR
            else:
                ruasAdjacencias[ruaNome] = set([adjacencias.group(1),adjacencias.group(2)])
        else:
            if ruaNome not in ruasAdjacencias:
                ruasAdjacencias[ruaNome] = set([])

# Escrita para o ficheiro pl
fwrite = open("baseConhecimento.pl","w+",encoding='utf-8')

capacidadeCamiao = 15000
fwrite.write(f"% ------ Capacidade do camiao ------\n\n")
fwrite.write(f"camiao({capacidadeCamiao}).\n\n")


contentores.sort(key = lambda x: x[3])

fwrite.write(f"% ------ Pontos de recolha ------\n\n")
garagem = (1,-9.1333,38.69864)
deposito = (2, -9.149924625043745,38.71161385709514)
fwrite.write(f"garagem{garagem}.\n")
fwrite.write(f"deposito{deposito}.\n\n") 
for ptRecolha in sorted(pontosRecolha.values()):
    fwrite.write(f"pontoRecolha({ptRecolha[0]},{ptRecolha[1]},{ptRecolha[2]},'{ptRecolha[3]}').\n")

fwrite.write(f"\n\n% ------ Contentores ------\n\n")
for cont in contentores:
    fwrite.write(f"contentor({cont[0]},'{cont[1]}',{cont[2]},{cont[3]}).\n")



fwrite.write(f"\n\n% ------ Ruas Adjacentes ------\n\n")
ruasAdjacenciasRes = ruasAdjacencias
for ruaNome, ruaAdj in sorted(ruasAdjacencias.items()):
    # Retirar ruas às quais não pertence nenhum ponto de recolha
    pr = list(sorted(ruaAdj))
    for p in pr:
        if p not in ruasAdjacencias.keys():
            pr.remove(p)
        elif p == ruaNome:
            pr.remove(p)
    # Retirar ruas que nao possuem quaisquer adjacencias
    ruasAdjacenciasRes[ruaNome] = set(pr)  
    if len(pr) == 0:
        for key, value in ruasAdjacencias.items():
            if key != ruaNome and (ruaNome in value):
                fwrite.write(f"adjacenteRuas('{ruaNome}',[")
                pr.append(key)
                fwrite.write(f"'{pr[0]}'")
                fwrite.write(f"]).\n")
                
        if len(pr) == 0:
            ruasAdjacenciasRes.pop(ruaNome)
        else:
            ruasAdjacenciasRes[ruaNome] = set(pr)
    else:
        fwrite.write(f"adjacenteRuas('{ruaNome}',[")
        for i in range(0, len(pr)):
            if i == (len(pr)-1):
                fwrite.write(f"'{pr[i]}'")
            else :
                fwrite.write(f"'{pr[i]}',")
        fwrite.write(f"]).\n")

#print(ruasAdjacenciasRes)
#print(len(ruasAdjacenciasRes))

fwrite.write(f"\n\n% ------ Ruas ------\n\n")
idRua = 1
ruaRes = {}
for ruaN in sorted(ruasAdjacenciasRes.keys()):
    rua = ruas[ruaN]
    fwrite.write(f"rua({idRua},'{rua[0]}',[")
    pr = list(sorted(rua[1]))
    for i in range(0, len(pr)):
        if i == (len(pr)-1):
            fwrite.write(f"{pr[i]}")
        else :
            fwrite.write(f"{pr[i]},")
    fwrite.write(f"]).\n")
    idRua+=1
    ruaRes[ruaN] = rua

# Calcular adjacencias entre pontos de recolha
G = nx.Graph()
fwrite.write(f"\n\n% ------ Adjacencias entre pontos de recolha ------\n\n")
pontosAdjacentes = set() # todos os pontos que pertencem ao grafo
for rua in ruaRes.values():
    listaPtR = list(sorted(rua[1]))
    for i in range(0, len(listaPtR)-1):
        id1 = listaPtR[i]
        id2 = listaPtR[i+1]
        dist = distancia(float(pontosRecolha[id1][1]),float(pontosRecolha[id1][2]),float(pontosRecolha[id2][1]),float(pontosRecolha[id2][2]))
        fwrite.write(f"aresta({id1},{id2},{dist}).\n")
        pontosAdjacentes.add(id1)
        pontosAdjacentes.add(id2)
        G.add_edge(id1, id2, weight=dist)
    listaAdj = ruasAdjacenciasRes[rua[0]]
    for nomeRuaAdj in listaAdj:
        if nomeRuaAdj in ruaRes:
            listaPr = list(sorted(ruaRes[nomeRuaAdj][1]))
            if listaPr[0] < listaPtR[0]:
                id1 = listaPr[0]
                id2 = listaPtR[0]
                dist = distancia(float(pontosRecolha[id1][1]),float(pontosRecolha[id1][2]),float(pontosRecolha[id2][1]),float(pontosRecolha[id2][2]))
                fwrite.write(f"aresta({id1},{id2},{dist}).\n")
                G.add_edge(id1, id2, weight=dist)
            else:
                id1 = listaPr[0]
                id2 = listaPtR[len(listaPtR)-1]
                dist = distancia(float(pontosRecolha[id1][1]),float(pontosRecolha[id1][2]),float(pontosRecolha[id2][1]),float(pontosRecolha[id2][2]))
                fwrite.write(f"aresta({id1},{id2},{dist}).\n")
                G.add_edge(id1, id2, weight=dist)
            pontosAdjacentes.add(id1)
            pontosAdjacentes.add(id2)

fwrite.write(f"\n\n% ------ Adjacencias de pontos de recolha para deposito e garagem ------\n\n")
for idPonto in sorted(pontosAdjacentes):
    dist = distancia(float(pontosRecolha[idPonto][1]),float(pontosRecolha[idPonto][2]),float(garagem[1]),float(garagem[2]))
    fwrite.write(f"aresta({garagem[0]},{idPonto},{dist}).\n")
    dist = distancia(float(pontosRecolha[idPonto][1]),float(pontosRecolha[idPonto][2]),float(deposito[1]),float(deposito[2]))
    fwrite.write(f"aresta({deposito[0]},{idPonto},{dist}).\n")
    

dist = distancia(float(deposito[1]),float(deposito[2]),float(garagem[1]),float(garagem[2]))
fwrite.write(f"arestaRegresso({deposito[0]},{garagem[0]},{dist}).\n")







#options = {'node_size' : 10,'font_size' : 1}
##nx.draw_networkx(G, with_labels = True,**options)
##nx.draw(G, with_labels=True,with_edges = True, node_size=5, node_color="skyblue", node_shape="s", alpha=0.5, linewidths=4)
#pos=nx.spring_layout(G) # pos = nx.nx_agraph.graphviz_layout(G)
#nx.draw(G,pos, with_labels=True,with_edges = True, node_size=5, node_color="skyblue", node_shape="s", alpha=0.5, linewidths=4)
#labels = nx.get_edge_attributes(G,'weight')
#nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
#plt.savefig("grafo.png")
#plt.show()

            


    











        