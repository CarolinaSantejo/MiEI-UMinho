import re
from collections import Counter

f = open("processos.xml", "r")

listaAnos = []
listaSeculos = []
listaDatas = []
min = []
max = []
min2 = ''
max2 = ''

for linha in f:
    res = re.search(r'<data>((((\d)|(\d){2})\d{2})-.+)</data>',linha)
    #group(1) -> Data
    #group(2) -> Ano
    #group(3) -> Século - 1

    if (res) :
        listaAnos.append(res.group(2))
        listaSeculos.append(int(res.group(3)) + 1)
        listaDatas.append(res.group(1))
        ''' 
        "work smarter not harder"
        resData = re.split(r'-',res.group(1),0,0)
        if(min== [] and max==[]):
            min=resData
            max=resData
        elif(int(resData[0])> int(max[0])):
            max = resData
        elif(int(resData[0])< int(min[0])):
            min = resData
        elif(int(resData[0])==int(min[0])):
            if(int(resData[1])<int(min[1])):
                min = resData
            elif (int(resData[1])==int(min[1])):
                if(int(resData[2])<int(min[2])):
                    min = resData
        elif (int(resData[0])==int(max[0])):
            if(int(resData[1])>int(max[1])):
                max = resData
            elif (int(resData[1])==int(max[1])):
                if(int(resData[2])>int(max[2])):
                    max = resData

print(min)
print(max) '''

listaAnos.sort()
listaSeculos.sort()


#Solução sem ER para descobrir o intervalo das datas 
listaDatas.sort()
min2 =listaDatas[0]
max2 = listaDatas[len(listaDatas)-1]
print("\n\033[1m\033[36mIntervalo de datas dos registos:\033[0m   [",min2,", ",max2,"]\n")

print("\033[1m\033[36mNúmero de Processos de cada ano dos registos (ordem cronologica):\033[0m")

for key, value in (sorted(Counter(listaAnos).items())):
    print("\033[93m->\033[0m", key, value)

i=0
for key in (Counter(listaSeculos).keys()):
    i+=1

print("\n\033[1m\033[36mNúmero de séculos analisados:\033[0m", i)
for key in (Counter(listaSeculos).keys()):
    print("\033[93m->\033[0m"," Século ", key)




'''
#Solução sem ER para obter os vários séculos
seculos = []
for x in Counter(listaAnos).keys():
    y = (int(x)/100)+1
    seculos.append(int(y))
print(Counter(seculos).keys())

'''


