import re
from collections import Counter
#from graphviz import Digraph

f = open("processos.xml", "r")
content = f.read()
i = 0
lista = []

def procuraIrmao(id,lista):
    for i in lista:
        processo = i[0]
        m = re.search(rf'<processo id="({id})">', processo)
        if (m):
            rnome = re.search(r'<nome>(([A-Z][a-z]+) ?([A-Z][a-z]+ )*([A-Z][a-z]+)?).*</nome>', processo)
            if(rnome):
                nome = rnome.group(1)
                lista.remove(i)
                return nome
    return False

def desenhaFamilias(familia,dot,i):
    mae = str(i)
    pai = str(i+1)
    dot.node(mae, familia[1])   #mae
    dot.node(pai, familia[2])   #pai
    dot.node(str(i+2), familia[0])   #filho

    dot.edge(mae, str(i+2))
    dot.edge(pai, str(i+2))
    i = i+3
    if len(familia) > 3:
        for x in range(3,len(familia)):
            dot.node(str(i), familia[x])
            dot.edge(mae, str(i))
            dot.edge(pai, str(i))

    #print(dot.source)
    return (dot,i)


def processosAno(ano):
    treeFam = []
    lista = re.findall(rf'(<processo id="(\d+)">\n *<pasta>\d+</pasta>\n *<data>({ano})-.+(\n|.)*?</processo>)', content)
    for i in lista:
        familia = []
        processo = i[0]
        rfilho = re.search(r'<nome>(([A-Z][a-z]+) ?([A-Z][a-z]+ )*([A-Z][a-z]+)?).*</nome>', processo)
        rmae = re.search(r'<mae>(([A-Z][a-z]+) ?([A-Z][a-z]+ )*([A-Z][a-z]+)?).*</mae>', processo)
        rpai = re.search(r'<pai>(([A-Z][a-z]+) ([A-Z][a-z]+ )*([A-Z][a-z]+)).*</pai>', processo)
        if (rmae):
            mae = rmae.group(1)
        else:
            mae = False  # caso não tenha mãe
        if (rpai):
            pai = rpai.group(1)
        else:
            pai = False  # caso não tenha pai
        if (rfilho):
            filho = rfilho.group(1)
            familia.append(filho)
            familia.append(mae)
            familia.append(pai)
        if re.search(r'<obs>', processo):
            res2 = re.findall(r'(Irmao). *Proc.(\d+)', processo)
            if (res2):
                for x in res2:
                    irmao = procuraIrmao(x[1],lista)
                    if irmao !=False:
                        familia.append(irmao)

        treeFam.append(familia)
    return treeFam



print("\nIntroduza o ano: ")
ano = input()
treeFam = processosAno(ano)
#dot = Digraph(comment=f'Arvore Genealogica dos Candidatos do Ano {ano}', engine = 'neato', format='png')

for familia in treeFam:
    print(familia)
    #(dot,i) = desenhaFamilias(familia,dot,i)
    i=i+1

#dot.render(f'test-output/arvoreGenealogica{ano}.gv', view=True)