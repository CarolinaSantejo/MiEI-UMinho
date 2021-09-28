

import re
from collections import Counter

f = open("processos.xml", "r")

contador = 0
listaP = []

for linha in f:
    if res := re.search(r'<obs>', linha):
        res2 = re.findall(r'(Irmao|Tio|Primo).',linha)
        # group(1) -> Observação Completa
        # group(2) -> Nome do Parente
        # group(3) -> Grau de Parentsco

        if (res2):
            contador+=1
            for x in res2:
                listaP.append(x)

parFreq = (Counter(listaP).most_common(1))

print("\n\033[36mGrau de Parentesco mais frequente:\033[0m ")
for key, value in parFreq:
    print("\033[93m->\033[0m",key)

print("\n\033[36mNúmero de candidatos que têm parentes eclesiásticos:\033[0m ")
print("\033[93m->\033[0m", contador)
