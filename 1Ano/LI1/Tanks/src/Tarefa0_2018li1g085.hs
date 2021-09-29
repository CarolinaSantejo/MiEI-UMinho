-- | Este módulo define funções genéricas sobre vetores e matrizes, que serão úteis na resolução do trabalho prático.
module Tarefa0_2018li1g085 where

import LI11819
import Data.List

-- * Funções não-recursivas.

-- | Um 'Vetor' é uma 'Posicao' em relação à origem.
type Vetor = Posicao
-- ^ <<http://oi64.tinypic.com/mhvk2x.jpg vetor>>

-- ** Funções sobre vetores.

-- *** Funções gerais sobre 'Vetores'.

-- | Soma dois 'Vetores'
somaVetores :: Vetor -> Vetor -> Vetor
somaVetores (a,b) (c,d) = (a+c,b+d) 

-- | Subtrai dois 'Vetores'.
subtraiVetores :: Vetor -> Vetor -> Vetor
subtraiVetores (a,b) (c,d) = (a-c,b-d)

-- | Multiplica um escalar por um 'Vetor'.
multiplicaVetor :: Int -> Vetor -> Vetor
multiplicaVetor i (a,b) = (a*i,b*i)

-- | Roda um 'Vetor' 90º no sentido dos ponteiros do relógio, alterando a sua direção sem alterar o seu comprimento (distância à origem).
--
-- <<http://oi65.tinypic.com/2j5o268.jpg rodaVetor>>
rodaVetor :: Vetor -> Vetor
rodaVetor (a,b) = (b,-a)

-- | Espelha um 'Vetor' na horizontal (sendo o espelho o eixo vertical).
--
-- <<http://oi63.tinypic.com/jhfx94.jpg inverteVetorH>>
inverteVetorH :: Vetor -> Vetor
inverteVetorH (a,b) = (a,-b)

-- | Espelha um 'Vetor' na vertical (sendo o espelho o eixo horizontal).
--
-- <<http://oi68.tinypic.com/2n7fqxy.jpg inverteVetorV>>
inverteVetorV :: Vetor -> Vetor
inverteVetorV (a,b) = (-a,b) 

-- *** Funções do trabalho sobre 'Vetor'es.

-- | Devolve um 'Vetor' unitário (de comprimento 1) com a 'Direcao' dada.
direcaoParaVetor :: Direcao -> Vetor
direcaoParaVetor d | d == C = (-1,0)
                   | d == D = (0,1)
                   | d == B = (1,0)
                   | d == E = (0,-1)

-- ** Funções sobre listas

-- *** Funções gerais sobre listas.
--
-- Funções não disponíveis no 'Prelude', mas com grande utilidade.

-- | Verifica se o indice pertence à lista.

eIndiceListaValido :: Int -> [Int] -> Bool
eIndiceListaValido i [] = False
eIndiceListaValido i l | (length l>i) && (i>=0) = True
                       | otherwise = False 

-- ** Funções sobre matrizes.

-- *** Funções gerais sobre matrizes.

-- | Uma matriz é um conjunto de elementos a duas dimensões.
--
-- Em notação matemática, é geralmente representada por:
--
-- <<https://upload.wikimedia.org/wikipedia/commons/d/d8/Matriz_organizacao.png matriz>>
type Matriz a = [[a]]

-- | Calcula a dimensão de uma matriz.
--
-- __NB:__ Note que não existem matrizes de dimensão /m * 0/ ou /0 * n/, e que qualquer matriz vazia deve ter dimensão /0 * 0/.

dimensaoMatriz :: Matriz a -> Dimensao
dimensaoMatriz [[]] = (0,0)
dimensaoMatriz [] = (0,0) 
dimensaoMatriz (h:t) | (length h == 0) = dimensaoMatriz t
                     | otherwise = (length (h:t) , length h)

-- Funçâo que elimina as listas vazias dentro da matriz
eVaziaD :: Matriz a -> Matriz a
eVaziaD [[]] = [[]]
eVaziaD [] = []
eVaziaD (h:t) | (length h)==0 = eVaziaD t
              | otherwise = h : eVaziaD t

comparaInts :: (Int,Int) -> (Int,Int) -> Bool
comparaInts (a,b) (c,d) | (a>=c)||(a<0)||(b>=d)||(b<0) = False
comparaInts (a,b) (c,d) | otherwise = True


-- | Verifica se a posição pertence à matriz.
ePosicaoMatrizValida :: Posicao -> Matriz a -> Bool 
ePosicaoMatrizValida (x,y) m = comparaInts (x,y) (dimensaoMatriz m)

comparaBorda :: (Int,Int) -> (Int,Int) -> Bool
comparaBorda (a,b) (c,d) | (a==(c-1)) || (a==0) || (b==(d-1)) || (b==0) = True
comparaBorda (a,b) (c,d) | otherwise = False

-- | Verifica se a posição está numa borda da matriz.
eBordaMatriz :: Posicao -> Matriz a -> Bool
eBordaMatriz (a,b) m = comparaBorda (a,b) (dimensaoMatriz m)

-- *** Funções do trabalho sobre matrizes.

-- | Converte um 'Tetromino' (orientado para cima) numa 'Matriz' de 'Bool'.
--
-- <<http://oi68.tinypic.com/m8elc9.jpg tetrominos>>
tetrominoParaMatriz :: Tetromino -> Matriz Bool
tetrominoParaMatriz t | t == I = [[False, True,False,False],[False, True,False,False],[False, True,False,False],[False, True,False,False]]
                      | t == J = [[False, True, False],[False, True, False],[True,True,False]]
                      | t == L = [[False, True, False],[False, True, False],[False,True,True]]
                      | t == O = [[True,True],[True,True]]
                      | t == S = [[False,True,True],[True,True,False],[False,False,False]]
                      | t == T = [[False,False,False],[True,True,True],[False, True, False]]
                      | t == Z = [[True,True,False],[False,True,True],[False,False,False]]

-- * Funções recursivas.

-- ** Funções sobre listas.
--
-- Funções não disponíveis no 'Prelude', mas com grande utilidade.

-- | Devolve o elemento num dado índice de uma lista.

encontraIndiceLista :: Int -> [a] -> a
encontraIndiceLista i (x:xs) | i == 0 = x
                             | otherwise = encontraIndiceLista (i-1) xs

-- | Modifica um elemento num dado índice.
--
-- __NB:__ Devolve a própria lista se o elemento não existir.

atualizaIndiceLista :: Int -> a -> [a] -> [a]
atualizaIndiceLista i a [] = []
atualizaIndiceLista 0 a (x:xs) = (a:xs)
atualizaIndiceLista i a (x:xs) = x:(atualizaIndiceLista (i-1) a xs)

-- ** Funções sobre matrizes.

-- | Roda uma 'Matriz' 90º no sentido dos ponteiros do relógio.
--
-- <<http://oi68.tinypic.com/21deluw.jpg rodaMatriz>>

rodaMatriz :: Matriz a -> Matriz a
rodaMatriz [] = []
rodaMatriz ([]:_) = []
rodaMatriz a = (reverse (map head a)) : (rodaMatriz (map tail a))


-- | Inverte uma 'Matriz' na horizontal.
--
-- <<http://oi64.tinypic.com/iwhm5u.jpg inverteMatrizH>>
inverteMatrizH :: Matriz a -> Matriz a
inverteMatrizH [] = []
inverteMatrizH (h:t) = (reverse h: inverteMatrizH t)

-- | Inverte uma 'Matriz' na vertical.
--
-- <<http://oi64.tinypic.com/11l563p.jpg inverteMatrizV>>
inverteMatrizV :: Matriz a -> Matriz a
inverteMatrizV m = reverse m

-- | Cria uma nova 'Matriz' com o mesmo elemento.
criaMatriz :: Dimensao -> a -> Matriz a
criaMatriz (x,y) a = replicate x (replicate y a)

-- | Devolve o elemento numa dada 'Posicao' de uma 'Matriz'.
encontraPosicaoMatriz :: Posicao -> Matriz a -> a
encontraPosicaoMatriz (0,0) (h:t) = head h
encontraPosicaoMatriz (0,y) (h:t) = encontraPosicaoMatriz (0,y-1) (tail(h):t)
encontraPosicaoMatriz (x,y) (h:t) | ePosicaoMatrizValida (x,y) (h:t) = encontraPosicaoMatriz (x-1,y) t
                                  | otherwise = undefined
        
-- | Modifica um elemento numa dada 'Posicao'
--
-- __NB:__ Devolve a própria 'Matriz' se o elemento não existir.

atualizaPosicaoMatriz :: Posicao -> a -> Matriz a -> Matriz a
atualizaPosicaoMatriz (0,0) a ((h:hs):t) = ((a:hs):t)
atualizaPosicaoMatriz (0,y) a (h:t) = (atualizaIndiceLista y a h:t)
atualizaPosicaoMatriz (x,y) a (h:t) = h:(atualizaPosicaoMatriz (x-1,y) a t)

