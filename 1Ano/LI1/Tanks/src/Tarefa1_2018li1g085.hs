-- | Tarefa 1 

module Tarefa1_2018li1g085 where

import LI11819
import Tarefa0_2018li1g085
import Data.List

-- * Testes

-- | Testes unitários da Tarefa 1.
testesT1 :: [Instrucoes]
testesT1 = [[Move C,Desenha],[Move B, Roda ,Desenha],[Move D,MudaTetromino,Desenha],[Move E,Desenha],
            [Move D,Move D,Move B,Move B,Move D,Move D,Move C,MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,Move E,Move B,Move E,MudaTetromino,MudaTetromino,Move B,Move D,Move C,Move C,MudaTetromino,MudaTetromino],
            [Move D,Move B,Move B,Move C, Roda ,Move B,Move D,Move C,MudaTetromino,MudaTetromino,Move E,Roda,MudaParede,Move B,Move E,Move B,Move E,Move C,Move C,Move D,Desenha,Move D,Move D,MudaParede,Move D,Move D,Move D,Move B,Move E,Desenha],
            [MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,Roda,Roda,Roda,MudaTetromino,MudaTetromino,Desenha],
            [MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,Roda,Roda,Roda,Roda,Desenha],
            [MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,Desenha],
            [MudaTetromino,MudaTetromino,MudaTetromino,MudaTetromino,Desenha],
            [MudaTetromino,MudaTetromino,MudaTetromino,Desenha],
            [MudaTetromino,MudaTetromino,Desenha]]

-- * Funções principais da Tarefa 1.

-- | Aplica uma 'Instrucao' num 'Editor'.
--
--    * 'Move' - move numa dada 'Direcao'.
--
--    * 'MudaTetromino' - seleciona a 'Peca' seguinte (usar a ordem léxica na estrutura de dados),
--       sem alterar os outros parâmetros.
--
--    * 'MudaParede' - muda o tipo de 'Parede'.
--
--    * 'Desenha' - altera o 'Mapa' para incluir o 'Tetromino' atual, sem alterar os outros parâmetros.

-- | Funçao auxiliar que muda a parede
mudaParede :: Parede -- ^ Parede incial
           -> Parede -- ^ Parede final
mudaParede p | p == Indestrutivel = Destrutivel
             | p == Destrutivel = Indestrutivel

-- | Funçao auxiliar que altera o Tetromino
mudaTetromino :: Tetromino -- ^ Tetromino inicial
              -> Tetromino -- ^ Tetromino final
mudaTetromino t | t == I = J
                | t == J = L
                | t == L = O
                | t == O = S
                | t == S = T
                | t == T = Z
                | t == Z = I


-- | Funçao auxiliar que muda a direçao
rodaDirecao :: Direcao -- ^ direçao inicial
            -> Direcao -- ^ direçao final
rodaDirecao d | d == C = D
              | d == D = B
              | d == B = E
              | d == E = C

-- | Funçao que coloca um bool em forma de bloco indestrutivel no mapa
colocaMapa :: Bool -> Parede -> Posicao -> Mapa -> Mapa
colocaMapa b p (x,y) m | b = atualizaPosicaoMatriz (x,y) (Bloco p) m
                       | otherwise = m

-- | Funçao que coloca uma lista de Bool num mapa em forma de blocos indestrutiveis
desenhaLista :: [Bool] -> Parede -> Posicao -> Posicao -> Mapa -> Mapa
desenhaLista [] _ _ _ m = m
desenhaLista (h:t) p (x1,y1) (x2,y2) m | y1 < y2 = desenhaLista t p (x1,y1+1) (x2,y2) (colocaMapa h p (x1,y1) m)
                                     | otherwise = m

-- | Funcao que desenha a matriz de Bool no mapa em forma de Blocos indistrutiveis 
desenhaMatriz :: Matriz Bool -> Parede -> Posicao -> Posicao -> Mapa -> Mapa
desenhaMatriz [] _ _ _ m = m
desenhaMatriz (h:t) p (x1,y1) (x2,y2) m | (x1 < x2) && (y1 < y2) = desenhaMatriz t p (x1+1,y1) (x2,y2) (desenhaLista h p (x1,y1) (x2,y2) m)
                                        | otherwise = m
                                      

-- | Aplica uma instruçao num Editor
instrucao :: Instrucao -- ^ A 'Instrucao' a aplicar.
          -> Editor    -- ^ O 'Editor' anterior.
          -> Editor    -- ^ O 'Editor' resultante após aplicar a 'Instrucao'.
instrucao i (Editor (y,x) d t p m) | i == Move C = Editor (y-1,x) d t p m 
                                   | i == Move B = Editor (y+1,x) d t p m
                                   | i == Move E = Editor (y,x-1) d t p m
                                   | i == Move D = Editor (y,x+1) d t p m
                                   | i == Roda = Editor (y,x) (rodaDirecao d) t p m
                                   | i == MudaTetromino = Editor (y,x) d (mudaTetromino t) p m
                                   | i == MudaParede = Editor (y,x) d t (mudaParede p) m
                                   | i == Desenha && d == C = Editor (y,x) d t p (desenhaMatriz (tetrominoParaMatriz t) p (y,x) (somaVetores (y,x) (dimensaoMatriz (tetrominoParaMatriz t))) m)
                                   | i == Desenha && d == D = Editor (y,x) d t p (desenhaMatriz (rodaMatriz(tetrominoParaMatriz t)) p (y,x) (somaVetores (y,x) (dimensaoMatriz (rodaMatriz(tetrominoParaMatriz t)))) m)
                                   | i == Desenha && d == B = Editor (y,x) d t p (desenhaMatriz (rodaMatriz(rodaMatriz(tetrominoParaMatriz t))) p (y,x) (somaVetores (y,x) (dimensaoMatriz (rodaMatriz(rodaMatriz(tetrominoParaMatriz t))))) m)
                                   | i == Desenha && d == E = Editor (y,x) d t p (desenhaMatriz (rodaMatriz(rodaMatriz(rodaMatriz(tetrominoParaMatriz t)))) p (y,x) (somaVetores (y,x) (dimensaoMatriz (rodaMatriz(rodaMatriz(rodaMatriz(tetrominoParaMatriz t)))))) m)
                                                      

-- | Aplica uma sequência de 'Instrucoes' num 'Editor'.
instrucoes :: Instrucoes -- ^ As 'Instrucoes' a aplicar.
           -> Editor     -- ^ O 'Editor' anterior.
           -> Editor     -- ^ O 'Editor' resultante após aplicar as 'Instrucoes'.
instrucoes [] e = e
instrucoes (h:t) e = instrucoes t (instrucao h e)

-- | Cria um 'Mapa' inicial com 'Parede's nas bordas e o resto vazio.
mapaInicial :: Dimensao -- ^ A 'Dimensao' do 'Mapa' a criar.
            -> Mapa     -- ^ O 'Mapa' resultante com a 'Dimensao' dada.
mapaInicial (x,y) = auxiliaMapa (0,0) (x,y) (mapaVazio (x,y))

-- Funçao auxiliar que coloca os blocos indestrutiveis na borda do mapa
auxiliaMapa :: Posicao -> Posicao -> Mapa -> Mapa
auxiliaMapa (a,b) (x,y) m | a==x-1 && b==y-1 = atualizaPosicaoMatriz (a,b) (Bloco Indestrutivel) m
                          | a==x-1 = auxiliaMapa (0,b+1) (x,y) (atualizaPosicaoMatriz (a,b) (Bloco Indestrutivel) m)
                          | b==y-1 = auxiliaMapa (a+1,b) (x,y) (atualizaPosicaoMatriz (a,b) (Bloco Indestrutivel) m)
                          | a==0 = auxiliaMapa (a+1,b) (x,y) (atualizaPosicaoMatriz (a,b) (Bloco Indestrutivel) m)
                          | b==0 = auxiliaMapa (a+1,b) (x,y) (atualizaPosicaoMatriz (a,b) (Bloco Indestrutivel) m)
                          | (0<a && a<x-1) && (0<b &&  b<y-1) = auxiliaMapa (a+1,b) (x,y) m
                          | otherwise = m 
                           

-- | Funçao auxiliar que cria um mapa de Peças Vazias
mapaVazio :: Dimensao -- ^ Dimensao do mapa a criar 
          -> Mapa     -- ^ Mapa de Peças Vazias
mapaVazio (x,y) = replicate x (replicate y Vazia)

-- | Cria um 'Editor' inicial.
editorInicial :: Instrucoes  -- ^ Uma sequência de 'Instrucoes' de forma a poder calcular a  'dimensaoInicial' e a 'posicaoInicial'.
              -> Editor      -- ^ O 'Editor' inicial, usando a 'Peca' 'I' 'Indestrutivel' voltada para 'C'.
editorInicial i = Editor (posicaoInicial i) C I Indestrutivel (mapaInicial (dimensaoInicial i)) 

-- | Funçao que, dado um editor, devolve um mapa
retornaM :: Editor
         -> Mapa
retornaM (Editor pi d t p m) = m

-- | Constrói um 'Mapa' dada uma sequência de 'Instrucoes'.
constroi :: Instrucoes -- ^ Uma sequência de 'Instrucoes' dadas a um 'Editor' para construir um 'Mapa'.
         -> Mapa       -- ^ O 'Mapa' resultante.
constroi i = retornaM (instrucoes i (editorInicial i))