from z3 import *
import numpy as np
from pandas import *
import re


file = "tab2.txt"
f = open(file, "r")

size = int(f.readline())


tab = [ [ Int(f"x_{i+1}_{j+1}") for j in range(size) ] for i in range(size) ]


# LISTA DE RESTRIÇÕES INICIAIS

# Cada valor entre 1 e 'size'
elems  = [ And(1 <= tab[i][j], tab[i][j] <= size) for i in range(size) for j in range(size) ]

# Números diferentes na mesma linha
linhas   = [ Distinct(tab[i]) for i in range(size) ]

# Números diferentes na mesma coluna
colunas   = [ Distinct([ tab[i][j] for i in range(size) ]) for j in range(size) ]

tabRInit = elems + linhas + colunas

s = Solver()
s.add(tabRInit)

# Parse do ficheiro

for linha in f:
    l = re.search(r'[^:]+',linha).group(0)
    if l.isdecimal():
        position = re.search(r'[^:]+: +(\d+) +(\d+)',linha)
        x = int(position.group(1))
        y = int(position.group(2))
        s.add(tab[x-1][y-1] == int(l))

    elif l == "<" or l == ">":
        position = re.search(r'[^:]+: *(\d+) +(\d+), *(\d+) +(\d+)',linha)
        x1 = int(position.group(1))
        y1 = int(position.group(2))
        x2 = int(position.group(3))
        y2 = int(position.group(4))
        if (l == "<"):
            s.add(tab[x1-1][y1-1] < tab[x2-1][y2-1])
        else:
            s.add(tab[x1-1][y1-1] > tab[x2-1][y2-1])
    else:
        print("Linha incorreta")
        break

# Resolução do tabuleiro

if s.check() == sat:
    m = s.model()
    r = [ [ m.evaluate(tab[i][j]) for j in range(size) ]
          for i in range(size) ]
    #print_matrix(r)
    a = np.array(r)
    for line in a:
        print ('  '.join(map(str, line)))
else:
    print("Não é possível resolver")