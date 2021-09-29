{-| 
Este módulo define funções comuns da Tarefa 6 do trabalho prático.

* __Autores__: A89500 - Carolina Gil Afonso Santejo; A84898 - Bernardo Maria Cortez Marques Nogueira da Graça

* __Grupo__: 2018li1g085 


* __Data__: 28 de Dezembro de 2018 


* __Docente__: João Alexandre Baptista Vieira Saraiva


= Introdução

Este documento foi a sexta tarefa fornecida aos alunos pelos docentes da disciplina de Laboratórios de Informática I no âmbito de 
avaliar as competências desenvolvidas ao longo do primeiro semestre do primeiro ano do curso Mestrado Integrado em Engenharia Informática na Universidade do Minho. 
Esta Tarefa em particular, deu liberdade aos alunos de se desafiarem entre si, na medida em que foi fornecido pelos docentes um torneio diário entre os vários grupos
para se defrontarem entre si de forma aleatória.
Para a realização deste trabalho foram utilizados três programas em simultâneo: Sublime Text, Haskell e Haddock. O primeiro teve a finalidade de editar todo o código para depois ser executado pelo compilador 
e por fim o Haddock teve como função melhorar a compreensão de todo o código escrito. Foi também importante o fornecimento, através do site da disciplina, 
de um simulador que possibilitou o teste dos vários cenários possiveis.
O principal problema a resolver nesta tarefa é a implementação de o máximo de hipóteses possíveis de acontecer durante um jogo de modo a obter, recorrendo à função 'bot',
um 'Jogador' que seja controlado pelo computador.

= Objetivos

O objetivo desta tarefa é implementar um robô que jogue Tanks automaticamente. 
A estratégia principal utilizada foi a aplicação de várias restrições de modo a testar o máximo de cenários possiveis.
As condições foram ordenadas por ordem de importância sendo as primeiras um mecanismo de defesa contra disparos adversários, de seguida aparecem aquelas que servem de ataque a um ou mais 'Jogador'es que se aproximem do 'bot',
posteriormente surgem as que destroem 'Bloco's 'Destrutivel' e por fim, se nenhuma destas se verificar o 'Jogador' vai mover-se pelo mapa.

= Conclusão

Em suma, com a realização deste trabalho foram obtidos resultados bastante bons tendo em conta todo o esforço depositado.
Com uma classificação média de 17º nos dois ultimos torneios realizados foi alcançado o objetivo proposto pelos docentes da disciplina.

-}

module Tarefa6_2018li1g085 where

import LI11819
import Tarefa0_2018li1g085
import Tarefa1_2018li1g085
import Tarefa2_2018li1g085
import Tarefa4_2018li1g085

-- * Funções principais da Tarefa 6.

-- | Define um ro'bot' capaz de jogar autonomamente o jogo.
bot :: Int          -- ^ O identificador do 'Jogador' associado ao ro'bot'.
    -> Estado       -- ^ O 'Estado' para o qual o ro'bot' deve tomar uma decisão.
    -> Maybe Jogada -- ^ Uma possível 'Jogada' a efetuar pelo ro'bot'.
bot i (Estado m js ds) | testaMesmaDirecao1 i botI ds = Just (Dispara Canhao)
                       | testaMesmaDirecao2 i botI ds = fogeTiros i botI ds
                       | contaJogDir botI jds m >= 2 = Just (Dispara Laser)
                       | contaParedeDestrutivel botI m >= 5 = Just (Dispara Laser)
                       | testaDirecao1 i botI ds = moveDisparos i botI ds
                       | testaMesmaDirecao botI jds m = Just (Dispara Canhao)
                       | testaDirecao botI jds m = jogParaDirecao botI jds m
                       | testaParedeDirecao botI m = Just (Dispara Canhao)
                       | testaParedeOutraDirecao botI m = jogParaDirecaoParede botI m
                       | testaJogMaisPerto botI jds && not (testaChoque2 i botI ds) = Just (Dispara Choque)
                       | testaJogPerto botI jds = jogPerto botI jds m
                       | otherwise = moveJogador botI m
                      where botI = encontraIndiceLista i js
                            jds = retiraIndiceLista i js

-- | Funçao que recebe uma 'PosicaoGrelha' uma 'Direcao' e um 'Mapa' e retorna a 'PosicaoGrelha' correspondente ao primeiro 'Bloco Indestrutivel' que encontrar na linha ou coluna correnpondente à sua 'Direcao'

encontraParedeIndestrutivel2 :: PosicaoGrelha -> Direcao -> Mapa -> PosicaoGrelha
encontraParedeIndestrutivel2 p d m | testaParedeIndestrutivel p d m = p
                                   | otherwise = encontraParedeIndestrutivel2 (somaVetores p (direcaoParaVetor d)) d m

-- | Função que testa se existe um 'Jogador' em frente ao 'Jogador' selecionado.

testaMesmaDirecao :: Jogador -> [Jogador] -> Mapa -> Bool
testaMesmaDirecao (Jogador (y,x) d v l c) [] m = False
testaMesmaDirecao (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) m | v1>0 && d==C && y1<y && (x1==x || x1==(x-1) || x1==(x+1)) && (y-y1)<=5 && y2<y1 = True
                                                                            | v1>0 && d==B && y1>y && (x1==x || x1==(x-1) || x1==(x+1)) && (y1-y)<=5 && y2>y1 = True
                                                                            | v1>0 && d==E && x1<x && (y1==y || y1==(y-1) || y1==(y+1)) && (x-x1)<=5 && x2<x1 = True
                                                                            | v1>0 && d==D && x1>x && (y1==y || y1==(y-1) || y1==(y+1)) && (x1-x)<=5 && x2>x1 = True
                                                                            | otherwise = testaMesmaDirecao (Jogador (y,x) d v l c) t m
                                    where (y2,x2) = encontraParedeIndestrutivel2 (y,x) d m

-- | Função que testa se existe um 'Jogador' na mesma linha ou coluna do 'Jogador' selecionado

testaDirecao :: Jogador -> [Jogador] -> Mapa -> Bool
testaDirecao (Jogador (y,x) d v l c) [] m = False
testaDirecao (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) m | v1>0 && d/=C && y1<y && (x1==x || x1==(x-1) || x1==(x+1)) && (y-y1)<=5 && yc<y1 = True
                                                                       | v1>0 && d/=B && y1>y && (x1==x || x1==(x-1) || x1==(x+1)) && (y1-y)<=5 && yb>y1 = True
                                                                       | v1>0 && d/=E && x1<x && (y1==y || y1==(y-1) || y1==(y+1)) && (x-x1)<=5 && xe<x1 = True
                                                                       | v1>0 && d/=D && x1>x && (y1==y || y1==(y-1) || y1==(y+1)) && (x1-x)<=5 && xd>x1 = True
                                                                       | otherwise = testaDirecao (Jogador (y,x) d v l c) t m                                                                      
                                                           where (yc,xc) = encontraParedeIndestrutivel2 (y,x) C m
                                                                 (yb,xb) = encontraParedeIndestrutivel2 (y,x) B m
                                                                 (ye,xe) = encontraParedeIndestrutivel2 (y,x) E m
                                                                 (yd,xd) = encontraParedeIndestrutivel2 (y,x) D m

-- | Função que conta quantos 'Jogador'es existem em frente ao 'Jogador' selecionado.

contaJogDir :: Jogador -> [Jogador] -> Mapa -> Int
contaJogDir (Jogador (y,x) d v l c) [] m = 0
contaJogDir (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) m | l>0 && v1>0 && d==C && y1<y && (x1==x || x1==(x-1) || x1==(x+1)) && y2<y1 = 1 + contaJogDir (Jogador (y,x) d v l c) t m
                                                                      | l>0 && v1>0 && d==B && y1>y && (x1==x || x1==(x-1) || x1==(x+1)) && y2>y1 = 1 + contaJogDir (Jogador (y,x) d v l c) t m
                                                                      | l>0 && v1>0 && d==E && x1<x && (y1==y || y1==(y-1) || y1==(y+1)) && x2<x1 = 1 + contaJogDir (Jogador (y,x) d v l c) t m
                                                                      | l>0 && v1>0 && d==D && x1>x && (y1==y || y1==(y-1) || y1==(y+1)) && x2>x1 = 1 + contaJogDir (Jogador (y,x) d v l c) t m
                                                                      | otherwise = contaJogDir (Jogador (y,x) d v l c) t m
                                                           where (y2,x2) = encontraParedeIndestrutivel2 (y,x) d m

-- | Função que retira de uma lista o elemento na posicao i.

retiraIndiceLista :: Int -> [a] -> [a]
retiraIndiceLista 0 (h:t) = t
retiraIndiceLista i (h:t) = h : retiraIndiceLista (i-1) t

-- | Função que movimenta o 'Jogador' selecionado na 'Direcao' onde existe outro 'Jogador' da lista a uma distância menor ou igual a 5.                                                                   

jogParaDirecao :: Jogador -> [Jogador] -> Mapa -> Maybe Jogada
jogParaDirecao j [] m = Nothing
jogParaDirecao (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) m | v1>0 && d/=C && y1<y && (x1==x || x1==(x-1) || x1==(x+1)) && (y-y1)<=5 && yc<y1 = Just (Movimenta C)
                                                                         | v1>0 && d/=B && y1>y && (x1==x || x1==(x-1) || x1==(x+1)) && (y1-y)<=5 && yb>y1 = Just (Movimenta B)
                                                                         | v1>0 && d/=E && x1<x && (y1==y || y1==(y-1) || y1==(y+1)) && (x-x1)<=5 && xe<x1 = Just (Movimenta E)
                                                                         | v1>0 && d/=D && x1>x && (y1==y || y1==(y-1) || y1==(y+1)) && (x1-x)<=5 && xd>x1 = Just (Movimenta D)
                                                                         | otherwise = jogParaDirecao (Jogador (y,x) d v l c) t m
                                                           where (yc,xc) = encontraParedeIndestrutivel2 (y,x) C m
                                                                 (yb,xb) = encontraParedeIndestrutivel2 (y,x) B m
                                                                 (ye,xe) = encontraParedeIndestrutivel2 (y,x) E m
                                                                 (yd,xd) = encontraParedeIndestrutivel2 (y,x) D m

-- | Funcao que testa se existe algum jogador adversario a uma distancia menor que 15 do 'bot'.

testaJogPerto :: Jogador -> [Jogador] -> Bool
testaJogPerto j [] = False
testaJogPerto (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) | v1>0 && dist (y1,x1) (y,x) <= 30 && (x1==x || x1==(x-1) || x1==(x+1)) = False
                                                                      | v1>0 && dist (y1,x1) (y,x) <= 30 && (y1==y || y1==(y-1) || y1==(y+1)) = False
                                                                      | v1>0 && dist (y1,x1) (y,x) <= 30 = True
                                                                      | otherwise = testaJogPerto (Jogador (y,x) d v l c) t
                                       where dist (a,b) (c,d) = (a-c)^2 + (b-d)^2

-- | Função que movimenta o 'Jogador' selecionado até ao 'Jogador' adversário que se encontra mais próximo que esteja a uma distância menor ou igual a 15

jogPerto :: Jogador -> [Jogador] -> Mapa -> Maybe Jogada
jogPerto (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) m | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) < dist (x,0) (x1,0) && y>y1 && testaParede (y,x) C m = Just (Movimenta C)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) < dist (x,0) (x1,0) && y<y1 && testaParede (y,x) B m = Just (Movimenta B)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) > dist (x,0) (x1,0) && x<x1 && testaParede (y,x) D m = Just (Movimenta D)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) > dist (x,0) (x1,0) && x>x1 && testaParede (y,x) E m = Just (Movimenta E)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) == dist (x,0) (x1,0) && y>y1 && testaParede (y,x) C m = Just (Movimenta C)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) == dist (x,0) (x1,0) && y<y1 && testaParede (y,x) B m = Just (Movimenta B)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) < dist (x,0) (x1,0) && y>y1 && not (testaParede (y,x) C m) && x<x1 = Just (Movimenta D)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) < dist (x,0) (x1,0) && y>y1 && not (testaParede (y,x) C m) && x>x1 = Just (Movimenta E) 
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) < dist (x,0) (x1,0) && y<y1 && not (testaParede (y,x) B m) && x<x1 = Just (Movimenta D)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) < dist (x,0) (x1,0) && y<y1 && not (testaParede (y,x) B m) && x>x1 = Just (Movimenta E)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) > dist (x,0) (x1,0) && x<x1 && not (testaParede (y,x) D m) && y<y1 = Just (Movimenta C)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) > dist (x,0) (x1,0) && x<x1 && not (testaParede (y,x) D m) && y>y1 = Just (Movimenta B)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) > dist (x,0) (x1,0) && x>x1 && not (testaParede (y,x) E m) && y<y1 = Just (Movimenta C)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) > dist (x,0) (x1,0) && x>x1 && not (testaParede (y,x) E m) && y>y1 = Just (Movimenta B)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) == dist (x,0) (x1,0) && y>y1 && not (testaParede (y,x) C m) && x<x1 = Just (Movimenta D)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) == dist (x,0) (x1,0) && y>y1 && not (testaParede (y,x) C m) && x>x1 = Just (Movimenta E)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) == dist (x,0) (x1,0) && y<y1 && not (testaParede (y,x) B m) && x<x1 = Just (Movimenta D)
                                                                   | v1>0 && dist (y,x) (y1,x1) <= 30 && dist (y,0) (y1,0) == dist (x,0) (x1,0) && y<y1 && not (testaParede (y,x) B m) && x>x1 = Just (Movimenta E)
                                                                   | otherwise = jogPerto (Jogador (y,x) d v l c) t m
                              where dist (a,b) (c,d) = (a-c)^2 + (b-d)^2

-- | Função que testa se existe um 'DisparoCanhao' em frente ao 'Jogador' selecionado.

testaMesmaDirecao1 :: Int -> Jogador -> [Disparo] -> Bool
testaMesmaDirecao1 i j [] = False
testaMesmaDirecao1 i (Jogador (y,x) d v l c) (DisparoCanhao i1 (y1,x1) d1:t) | i/=i1 && d==C && y1<y && (x1==x) && (y-y1)<=5 = True
                                                                             | i/=i1 && d==B && y1>y && (x1==x) && (y1-y)<=5 = True
                                                                             | i/=i1 && d==E && x1<x && (y1==y) && (x-x1)<=5 = True
                                                                             | i/=i1 && d==D && x1>x && (y1==y) && (x1-x)<=5 = True
                                                                             | otherwise = testaMesmaDirecao1 i (Jogador (y,x) d v l c) t
testaMesmaDirecao1 i j (_:t) = testaMesmaDirecao1 i j t

-- | Função que testa se existe um 'DisparoCanhao' em frente ao 'Jogador' selecionado mas numa linha ou coluna diferentes.

testaMesmaDirecao2 :: Int -> Jogador -> [Disparo] -> Bool
testaMesmaDirecao2 i j [] = False
testaMesmaDirecao2 i (Jogador (y,x) d v l c) (DisparoCanhao i1 (y1,x1) d1:t) | i/=i1 && d1==C && y1>y && (x1==(x-1)) || (x1==(x+1)) && (y1-y)<=5 = True
                                                                             | i/=i1 && d1==B && y1<y && (x1==(x-1)) || (x1==(x+1)) && (y-y1)<=5 = True
                                                                             | i/=i1 && d1==E && x1>x && (y1==(y-1)) || (y1==(y+1)) && (x1-x)<=5 = True
                                                                             | i/=i1 && d1==D && x1<x && (y1==(y-1)) || (y1==(y+1)) && (x-x1)<=5 = True
                                                                             | otherwise = testaMesmaDirecao2 i (Jogador (y,x) d v l c) t
testaMesmaDirecao2 i j (_:t) = testaMesmaDirecao2 i j t


-- | Função que movimenta um 'Jogador' para uma 'Direcao' com que se afaste de um 'DisparoCanhao' adversário que se aproxima.

fogeTiros :: Int -> Jogador -> [Disparo] -> Maybe Jogada
fogeTiros i j [] = Nothing
fogeTiros i (Jogador (y,x) d v l c) (DisparoCanhao i1 (y1,x1) d1:t) | i/=i1 && d1==C && y1>y && x1==(x-1) && (y1-y)<=5 = Just (Movimenta D)
                                                                    | i/=i1 && d1==C && y1>y && x1==(x+1) && (y1-y)<=5 = Just (Movimenta E)
                                                                    | i/=i1 && d1==B && y1<y && x1==(x-1) && (y-y1)<=5 = Just (Movimenta D)
                                                                    | i/=i1 && d1==B && y1<y && x1==(x+1) && (y-y1)<=5 = Just (Movimenta E)
                                                                    | i/=i1 && d1==E && x1>x && y1==(y+1) && (x1-x)<=5 = Just (Movimenta C)
                                                                    | i/=i1 && d1==E && x1>x && y1==(y-1) && (x1-x)<=5 = Just (Movimenta B)
                                                                    | i/=i1 && d1==D && x1<x && y1==(y-1) && (x-x1)<=5 = Just (Movimenta B)
                                                                    | i/=i1 && d1==D && x1<x && y1==(y+1) && (x-x1)<=5 = Just (Movimenta C)
                                                                    | otherwise = fogeTiros i (Jogador (y,x) d v l c) t
fogeTiros i j (_:t) = fogeTiros i j t

-- | Função que testa se existe um 'DisparoCanhao' na mesma linha ou coluna que o 'Jogador' selecionado.

testaDirecao1 :: Int -> Jogador -> [Disparo] -> Bool
testaDirecao1 i (Jogador (y,x) d v l c) [] = False
testaDirecao1 i (Jogador (y,x) d v l c) (DisparoCanhao i1 (y1,x1) d1:t) | i/=i1 && (y1==y || x1==x) && dist (y,x) (y1,x1)<=26  = True 
                                                                        | otherwise = testaDirecao1 i (Jogador (y,x) d v l c) t
                                                where dist (a,b) (c,d) = (a-c)^2 + (b-d)^2
testaDirecao1 i j (_:t) = testaDirecao1 i j t

-- | Função que move um 'Jogador' para uma 'Direcao' onde haja um 'DisparoCanhao'.

moveDisparos :: Int -> Jogador -> [Disparo] -> Maybe Jogada
moveDisparos i j [] = Nothing
moveDisparos i (Jogador (y,x) d v l c) (DisparoCanhao i1 (y1,x1) d1:t) | not (testaMesmaDirecao1 i (Jogador (y,x) d v l c) (DisparoCanhao i (y1,x1) d1:t)) && d/=C && y1<y && (x1==x) && (y-y1)<=5 = Just (Movimenta C)
                                                                       | not (testaMesmaDirecao1 i (Jogador (y,x) d v l c) (DisparoCanhao i (y1,x1) d1:t)) && d/=B && y1>y && (x1==x) && (y1-y)<=5 = Just (Movimenta B)
                                                                       | not (testaMesmaDirecao1 i (Jogador (y,x) d v l c) (DisparoCanhao i (y1,x1) d1:t)) && d/=E && x1<x && (y1==y) && (x-x1)<=5 = Just (Movimenta E)
                                                                       | not (testaMesmaDirecao1 i (Jogador (y,x) d v l c) (DisparoCanhao i (y1,x1) d1:t)) && d/=D && x1>x && (y1==y) && (x1-x)<=5 = Just (Movimenta D)
                                                                       | otherwise = moveDisparos i (Jogador (y,x) d v l c) t
moveDisparos i j (_:t) = moveDisparos i j t

-- | Função que conta quantas 'Parede's 'Destrutivel' existem na 'Direcao' do 'Jogador'
--
-- __NB:__ 1 pode corresponder a 1 ou 2 'Bloco'(s) 'Destrutivel'

contaParedeDestrutivel :: Jogador -> Mapa -> Int
contaParedeDestrutivel j [] = 0
contaParedeDestrutivel (Jogador (y,x) d v l c) m | l==0 || testaParedeIndestrutivel (y,x) d m = 0
                                                 | l>0 && testaParedeDestrutivel (y,x) d m && y>0 && x>0 && y<(length m - 2) && x<(length (head m) - 2) = 1 + contaParedeDestrutivel (Jogador (somaVetores (y,x) (direcaoParaVetor d)) d v l c) m
                                                 | otherwise = contaParedeDestrutivel (Jogador (somaVetores (y,x) (direcaoParaVetor d)) d v l c) m
                                                 

-- | Função que testa se existe um ou mais 'Bloco's 'Destrutivel' na direcao do 'Jogador'.

testaParedeDirecao :: Jogador -> Mapa -> Bool
testaParedeDirecao j [] = False
testaParedeDirecao (Jogador p d v l c) m | encontraParedeDestrutivel p d m /= (0,0) && dist (encontraParedeDestrutivel p d m) p <= 25 = True
                                         | otherwise = False 
                       where dist (a,b) (c,d) = (a-c)^2 + (b-d)^2

-- | Função que testa se existe um 'Bloco' 'Destrutivel' numa linha ou coluna diferentes da 'Direcao' do 'Jogador' correspondente.

testaParedeOutraDirecao :: Jogador -> Mapa -> Bool
testaParedeOutraDirecao j [] = False
testaParedeOutraDirecao (Jogador p d v l c) m | d/=C && encontraParedeDestrutivel p C m /= (0,0) && dist (encontraParedeDestrutivel p C m) p <= 25 = True
                                              | d/=B && encontraParedeDestrutivel p B m /= (0,0) && dist (encontraParedeDestrutivel p B m) p <= 25 = True
                                              | d/=E && encontraParedeDestrutivel p E m /= (0,0) && dist (encontraParedeDestrutivel p E m) p <= 25 = True
                                              | d/=D && encontraParedeDestrutivel p D m /= (0,0) && dist (encontraParedeDestrutivel p D m) p <= 25 = True
                                              | otherwise = False 
                              where dist (a,b) (c,d) = (a-c)^2 + (b-d)^2



-- | Função que recebendo uma 'PosicaoGrelha' e uma 'Direcao', retorna a 'PosicaoGrelha' onde se encontra o(s) bloco(s) 'Destrutivel' mais próximo(s) em frente à 'PosicaoGrelha' dada.
--
-- __NB:__ Retorna (0,0) se for encontrado primeiro algum bloco 'Indestrutivel'.

encontraParedeDestrutivel :: PosicaoGrelha -> Direcao -> Mapa -> PosicaoGrelha 
encontraParedeDestrutivel p d m | testaParedeDestrutivel p d m = p
                                | testaParedeIndestrutivel p d m = (0,0)
                                | otherwise = encontraParedeDestrutivel (somaVetores p (direcaoParaVetor d)) d m

-- | Função que movimenta um 'Jogador' para a 'PosicaoGrelha' seguinte válida.

moveJogador :: Jogador -> Mapa -> Maybe Jogada
moveJogador (Jogador p d v l c) m | testaParede p d m = Just (Movimenta d)
                                  | d/=B && d/=C && testaParede p C m = Just (Movimenta C)
                                  | d/=D && d/=E && testaParede p E m = Just (Movimenta E)
                                  | d/=E && d/=D && testaParede p D m = Just (Movimenta D)
                                  | d/=C && d/=B && testaParede p B m = Just (Movimenta B)
                                  | otherwise = Just (Movimenta (dirOposta d))
                          where dirOposta C = B
                                dirOposta B = C
                                dirOposta E = D
                                dirOposta D = E

-- | Função que movimenta um 'Jogador' na 'Direcao' de um 'Bloco' 'Destrutivel'.

jogParaDirecaoParede :: Jogador -> Mapa -> Maybe Jogada
jogParaDirecaoParede (Jogador (y,x) d v l c) m | d/=C && (yc,xc) /= (0,0) && (y-yc)<=5 = Just (Movimenta C)
                                               | d/=B && (yb,xb) /= (0,0) && (yb-y)<=5 = Just (Movimenta B)
                                               | d/=E && (ye,xe) /= (0,0) && (x-xe)<=5 = Just (Movimenta E)
                                               | d/=D && (yd,xd) /= (0,0) && (xd-x)<=5 = Just (Movimenta D)
                                               | otherwise = Nothing
                            where (yc,xc) = encontraParedeDestrutivel (y,x) C m
                                  (yb,xb) = encontraParedeDestrutivel (y,x) B m
                                  (ye,xe) = encontraParedeDestrutivel (y,x) E m
                                  (yd,xd) = encontraParedeDestrutivel (y,x) D m


-- | Função que testa se um 'Jogador' já disparou um 'DisparoChoque'.

testaChoque2 :: Int -> Jogador -> [Disparo] -> Bool
testaChoque2 i (Jogador p d v l c) [] = False
testaChoque2 i (Jogador p d v l c) (DisparoChoque i1 t1:t) | i==i1 || c==0 = True
                                                           | otherwise = testaChoque2 i (Jogador p d v l c) t
testaChoque2 i (Jogador p d v l c) (_:t) = testaChoque2 i (Jogador p d v l c) t


-- | Funcao que testa se existe algum jogador adversario a uma distancia menor que sqrt (18) do 'bot' que  não coincida com a mesma linha ou coluna.

testaJogMaisPerto :: Jogador -> [Jogador] -> Bool
testaJogMaisPerto j [] = False
testaJogMaisPerto (Jogador (y,x) d v l c) (Jogador (y1,x1) d1 v1 l1 c1:t) | v1>0 && dist (y1,x1) (y,x) <= 18 && (x1==x || x1==(x-1) || x1==(x+1)) = False
                                                                          | v1>0 && dist (y1,x1) (y,x) <= 18 && (y1==y || y1==(y-1) || y1==(y+1)) = False
                                                                          | v1>0 && dist (y1,x1) (y,x) <= 18 = True
                                                                          | otherwise = testaJogMaisPerto (Jogador (y,x) d v l c) t
                                       where dist (a,b) (c,d) = (a-c)^2 + (b-d)^2
