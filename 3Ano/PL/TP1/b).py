'''

<nome>([a-zA-Z]+)[a-zA-Z ]* ([a-zA-Z]+)<\/nome>
<nome>([A-Z][a-z]+) ([A-Z][a-z]+ )*([A-Z][a-z]+)<\/nome>

'''

import re
from collections import Counter
from operator import itemgetter
from itertools import groupby

f = open("processos.xml", "r")

listaProprios=[]
listaApelidos=[]
listaSecNomesP=[]
listaSecNomesA=[]
nomeP = ()
nomeA = ()
'''
for linha in f:
    if m := re.search(r'(?:(<processo>))?([^<]*)(?:(</processo>))?', linha.strip()):
        for g in m.groups():
            print(g)
'''

for linha in f:
    res = re.search(r'<nome>(([A-Z][a-z]+) ([A-Z][a-z]+ )*([A-Z][a-z]+))</nome>',linha)
    # group(1) -> Nome Completo
    # group(2) -> Primeiro Nome
    # group(4) -> Último nome

    res2 = re.search(r'<data>((((\d)|(\d){2})\d{2})-.+)</data>',linha)

    if(res):
        nomeP = res.group(2)
        nomeA = res.group(4)
        listaProprios.append(nomeP)
        listaApelidos.append(nomeA)
    if res2 and nomeP and nomeA:
        seculo = int(res2.group(3))+1
        listaSecNomesP.append((nomeP,seculo))
        listaSecNomesA.append((nomeA,seculo))
        nomeA = ()
        nomeP = ()


listaSecNomesP = sorted(listaSecNomesP, key=lambda x: x[1])
X1 = [list(group) for key, group in groupby(listaSecNomesP, itemgetter(1))]

listaSecNomesA = sorted(listaSecNomesA,  key=lambda x: x[1])
X2 = [list(group) for key, group in groupby(listaSecNomesA, itemgetter(1))]


words = Counter(listaProprios).most_common()
print("\033[1m\033[36mTop Global Nomes Próprios:\033[0m ")
for key, value in words:
    print(key, value)

words2 = Counter(listaApelidos).most_common()
print("\n\033[1m\033[36mTop Global Apelidos:\033[0m ")
for key, value in words2:
    print(key, value)
print("\n")


for i in X1:
    seculo = i[0][1]
    print("\033[1m\033[36mTop 5 Nomes Próprios do Século" , seculo ,"\033[0m" )
    for key, value in (Counter(i).most_common(5)):
        print("->",key[0], value)
    print("\n")

for i in X2:
    seculo = i[0][1]
    print("\033[1m\033[36mTop 5 Apelidos do Século" , seculo ,"\033[0m" )
    for key, value in (Counter(i).most_common(5)):
        print("->",key[0], value)
    print("\n")