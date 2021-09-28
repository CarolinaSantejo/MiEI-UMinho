import re
import sys
from collections import Counter

f = open("processos.xml", "r")

content = f.read()
print("\nIntroduza o ID do processo do candidato, para ver o nº de filhos dos seus pais: ")
filho = input()
irmaos = []
pais=[] #pais[0] = mãe; pais[1] = pai
traicoes=[]

def procuraID(id,filho1):
    m = re.search(rf'<processo id="({id})">(\n|.)*?</processo>', content)
    if (m):
        processo = m.group()
        rmae = re.search(r'<mae>(([A-Z][a-z]+) ?([A-Z][a-z]+ )*([A-Z][a-z]+)?).*</mae>',processo)
        rpai = re.search(r'<pai>(([A-Z][a-z]+) ([A-Z][a-z]+ )*([A-Z][a-z]+)).*</pai>',processo)
        if(rmae):
            mae = rmae.group(1)
        else:
            mae = '' #caso não tenha mãe
        if(rpai):
            pai = rpai.group(1)
        else:
            pai = '' #caso não tenha pai

        if(filho1 == 1): # é o 1º filho que procuramos, corresponde ao ID que o User fornece
            pais.append(mae)
            pais.append(pai)
            if re.search(r'<obs>', processo):
                res2 = re.findall(r'(Irmao). *Proc.(\d+)',processo)
                if(res2):
                    for x in res2:
                        irmaos.append(x[1])

        elif (filho1 == 0):  # é irmão

            if (mae == pais[0]):
                pais.append(mae)
            else:
                traicoes.append(mae) #caso o irmão tenha mãe diferente de um dos irmãos

            if (pai == pais[1]):
                pais.append(pai) #caso o irmão tenha pai diferente de um dos irmãos
            else:
                traicoes.append(pai)

'''
# Teste com todos os ids, verificando traições entre casais
lista = re.findall(rf'<processo id="(\d+)">',content)
for i in lista:
    print(i)
    procuraID(i,1)
    for x in irmaos:
        procuraID(x, 0)
    #print(Counter(pais))
    #print(irmaos)
    print("Traicoes: ",traicoes)
    pais=[]
    irmaos=[]
'''
procuraID(filho,1)
for i in irmaos:
    procuraID(i,0)

paisRes = Counter(pais).most_common()
print("\n\033[36m\033[1mPais + NºFilhos:\033[0m")
for key, value in paisRes:
    print(key,"\033[93m->\033[0m", value)

print("\n")
print("\033[36m\033[1mIrmãos do Candidato:\033[0m ")
print(irmaos)


#21000 -> processo distinto com o ID repetido
