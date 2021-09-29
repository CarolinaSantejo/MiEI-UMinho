-- | Este módulo define funções comuns da Tarefa 4 do trabalho prático.
module Tarefa4_2018li1g085 where

import LI11819
import Tarefa0_2018li1g085
import Tarefa1_2018li1g085
import Tarefa2_2018li1g085
import Data.List


-- * Testes
-- | Testes unitários da Tarefa 4.
--
-- Cada teste é um 'Estado'.
testesT4 :: [Estado]
testesT4 = [Estado m j1 d1,Estado m j2 d1,Estado m j3 d1,Estado m j1 d2,Estado m j1 d3,Estado m j1 d4,Estado m j2 d2,Estado m j2 d3,Estado m j2 d4,Estado m j3 d2,Estado m j3 d3,Estado m j3 d4,Estado m j4 d4,Estado m j5 d3,Estado m1 j5 d3, Estado m2 j6 d5]
               where m = mapaInicial (10,10)
                     m1 = [[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]]
                     m2 = [[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Indestrutivel],
                           [Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]]
                     j1 = [Jogador (1,1) D 5 3 3, Jogador (4,2) C 4 0 2, Jogador (5,6) B 5 3 2, Jogador (1,6) D 5 0 2]
                     j2 = [Jogador (1,1) D 5 3 3, Jogador (1,3) C 4 1 2, Jogador (4,6) B 5 3 2, Jogador (2,7) D 5 0 2]
                     j3 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2, Jogador (5,6) B 5 3 2, Jogador (7,2) D 5 0 0]
                     j4 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2, Jogador (7,2) C 4 0 2]
                     j5 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2]
                     j6 = [Jogador (1,1) D 5 3 3, Jogador (4,1) C 4 0 2, Jogador (4,7) B 5 3 2, Jogador (1,6) D 5 0 2]
                     d1 = [DisparoCanhao 2 (6,6) E, DisparoCanhao 2 (3,6) E, DisparoChoque 1 4]
                     d2 = [DisparoLaser 1 (3,4) C, DisparoCanhao 2 (6,6) E]
                     d3 = [DisparoLaser 0 (5,4) C, DisparoLaser 1 (5,5) E, DisparoChoque 0 0]
                     d4 = [DisparoLaser 2 (7,7) E, DisparoLaser 0 (7,2) B, DisparoCanhao 2 (3,6) E]
                     d5 = [DisparoLaser 1 (3,4) E, DisparoCanhao 2 (6,6) E,DisparoCanhao 2 (3,6) D]


-- * Funções principais da Tarefa 4.

-- | Avança o 'Estado' do jogo um 'Tick' de tempo.
--
-- __NB:__ Apenas os 'Disparo's afetam o 'Estado' do jogo com o passar do tempo.
--
-- __NB:__ Deve chamar as funções 'tickChoques', 'tickCanhoes' e 'tickLasers' pela ordem definida.
tick :: Estado -- ^ O 'Estado' anterior.
     -> Estado -- ^ O 'Estado' após um 'Tick'.
tick = tickChoques . tickCanhoes . tickLasers

-- | Avança o 'Estado' do jogo um 'Tick' de tempo, considerando apenas os efeitos dos tiros de 'Laser' disparados.
tickLasers :: Estado -> Estado
tickLasers (Estado m js ds) = Estado (mapaLasers m (filter f ds)) (jogLasers (filter f ds) js m) alteraLasers
                where alteraLasers = dispLasers (filter f ds) (ds \\ filter f ds) m

-- | Funcao que retorna o valor 'True' se e só se receber um 'DisparoLaser'

f :: Disparo -> Bool
f (DisparoLaser i p d) = True
f _ = False

-- | Funçao que altera uma lista de 'Jogador' mediante a condiçao de terem sido afetados por um ou mais 'DisparoLaser'.

jogLasers :: [Disparo] -> [Jogador] -> Mapa -> [Jogador]
jogLasers [] js m = js
jogLasers ds [] m = []
jogLasers (DisparoLaser i (y,x) d:t) (Jogador (y1,x1) d1 v l c:t1) m | v>0 && d==C && y1>y3 && y1<=y && (x1==x3 || x1==(x3-1) || x1==(x3+1)) = jogLasers t [Jogador (y1,x1) d1 (v-1) l c] m ++ jogLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                     | v>0 && d==B && y1<y3 && y1>=y && (x1==x3 || x1==(x3-1) || x1==(x3+1)) = jogLasers t [Jogador (y1,x1) d1 (v-1) l c] m ++ jogLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                     | v>0 && d==E && x1>x3 && x1<=x && (y1==y3 || y1==(y3-1) || y1==(y3+1)) = jogLasers t [Jogador (y1,x1) d1 (v-1) l c] m ++ jogLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                     | v>0 && d==D && x1<x3 && x1>=x && (y1==y3 || y1==(y3-1) || y1==(y3+1)) = jogLasers t [Jogador (y1,x1) d1 (v-1) l c] m ++ jogLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                     | v==0 = Jogador (y1,x1) d1 v l c : jogLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                     | otherwise = jogLasers t [Jogador (y1,x1) d1 v l c] m ++ jogLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                 where (y3,x3) = encontraParedeIndestrutivel (DisparoLaser i (y,x) d) m

-- | Funcao que considerando a 'PosicaoGrelha' de um 'DisparoLaser' encontra a 'PosicaoGrelha' onde o 'DisparoLaser' acaba.

encontraParedeIndestrutivel :: Disparo -> Mapa -> PosicaoGrelha
encontraParedeIndestrutivel (DisparoLaser i p d) m | testaParedeIndestrutivel p d m = p
                                                   | otherwise = encontraParedeIndestrutivel (DisparoLaser i (somaVetores p (direcaoParaVetor d)) d) m


-- | Funçao que altera um 'Mapa' considerando os 'DisparoLaser' existentes.

mapaLasers :: Mapa -> [Disparo] -> Mapa
mapaLasers m [] = m
mapaLasers m (DisparoLaser i p d:t) | testaParedeDestrutivel p d m = mapaLasers (mapaLasersAux m p d) t 
                                    | testaParedeIndestrutivel p d m = mapaLasers m t
                                    | otherwise = mapaLasers m (DisparoLaser i (somaVetores p (direcaoParaVetor d)) d:t) 

-- | Funçao que altera um 'Mapa' se uma 'PosicaoGrelha' de um 'DisparoLaser' atingiu uma parede 'Destrutivel'.

mapaLasersAux :: Mapa -> PosicaoGrelha -> Direcao -> Mapa
mapaLasersAux m (y,x) d | testaParedeDestrutivel (y,x) d m && d==C && y>0 && x>0 && x<(length m - 2) && y<(length (head m) - 2) = mapaLasersAux (atualizaPosicaoMatriz (y,x) Vazia (atualizaPosicaoMatriz (y,x+1) Vazia m)) (y-1,x) d
                        | testaParedeDestrutivel (y,x) d m && d==B && y>0 && x>0 && x<(length m - 2) && y<(length (head m) - 2) = mapaLasersAux (atualizaPosicaoMatriz (y+1,x) Vazia (atualizaPosicaoMatriz (y+1,x+1) Vazia m)) (y+1,x) d
                        | testaParedeDestrutivel (y,x) d m && d==E && y>0 && x>0 && x<(length m - 2) && y<(length (head m) - 2) = mapaLasersAux (atualizaPosicaoMatriz (y,x) Vazia (atualizaPosicaoMatriz (y+1,x) Vazia m)) (y,x-1) d
                        | testaParedeDestrutivel (y,x) d m && d==D && y>0 && x>0 && x<(length m - 2) && y<(length (head m) - 2) = mapaLasersAux (atualizaPosicaoMatriz (y,x+1) Vazia (atualizaPosicaoMatriz (y+1,x+1) Vazia m)) (y,x+1) d
                        | testaParedeIndestrutivel (y,x) d m = m
                        | otherwise = mapaLasersAux m (somaVetores (y,x) (direcaoParaVetor d)) d


-- | Funcao que recebe uma lista de 'DisparoLaser', outra com os restantes disparos e um mapa e devolve a lista resultante dos disparos que não foram atingidos pelos 'DisparoLaser' 

dispLasers :: [Disparo] -> [Disparo] -> Mapa -> [Disparo]
dispLasers _ [] m = []
dispLasers [] ds m = ds
dispLasers (DisparoLaser i (y,x) d:t) (DisparoCanhao i1 (y1,x1) d1:t1) m | d==C && y1<y && y1>y3 && (x1==x3 || x1==(x3-1) || x1==(x3+1)) = dispLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                         | d==B && y1>y && y1<y3 && (x1==x3 || x1==(x3-1) || x1==(x3+1)) = dispLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                         | d==E && x1<x && x1>x3 && (y1==y3 || y1==(y3-1) || y1==(y3+1)) = dispLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                         | d==D && x1>x && x1<x3 && (y1==y3 || y1==(y3-1) || y1==(y3+1)) = dispLasers (DisparoLaser i (y,x) d:t) t1 m
                                                                         | otherwise = dispLasers t [DisparoCanhao i1 (y1,x1) d1] m ++ dispLasers (DisparoLaser i (y,x) d:t) t1 m
                        where (y3,x3) = encontraParedeIndestrutivel (DisparoLaser i (y,x) d) m
dispLasers l (DisparoChoque i t:t1) m = DisparoChoque i t : dispLasers l t1 m

-- | Avança o 'Estado' do jogo um 'Tick' de tempo, considerando apenas os efeitos das balas de 'Canhao' disparadas.

tickCanhoes :: Estado -> Estado
tickCanhoes (Estado m js ds) = Estado (mapaCanhoes m (filter f1 ds)) (jogCanhoes js (filter f1 ds)) (filter f2 ds ++ mudaCanhoes)
                     where mudaCanhoes = alteraCanhoes1 (dispCanhoes (mapaCanhoes1 m (alteraCanhoes (filter f1 ds) js)))


-- | Funçao que retorna 'True' se e só se receber um 'DisparoCanhao'

f1 :: Disparo -> Bool
f1 (DisparoCanhao i p d) = True
f1 _ = False

-- | Funçao que retorna 'True' se e só se receber um 'DisparoChoque' ou um 'DisparoLaser'

f2 :: Disparo -> Bool
f2 (DisparoLaser i p d) = True
f2 (DisparoChoque i t) = True
f2 _ = False


-- | Funçao que altera uma lista de disparos 'Canhao' mediante a condiçao de terem afetado um jogador.

alteraCanhoes :: [Disparo] -> [Jogador] -> [Disparo]
alteraCanhoes [] js = []
alteraCanhoes ds [] = ds
alteraCanhoes (DisparoCanhao i p d:t) js = alteraCanhoesAux 0 (DisparoCanhao i p d:t) js
                                  where alteraCanhoesAux i1 [] js = []
                                        alteraCanhoesAux i1 ds [] = ds
                                        alteraCanhoesAux i1 (DisparoCanhao i p d:t) (Jogador p1 d1 v l c:t1) | v>0 && i/=i1 && dist (somaVetores p (direcaoParaVetor d)) p1 <= 2 = alteraCanhoesAux i1 t (Jogador p1 d1 v l c:t1)
                                                                                                             | v>0 && i==i1 && d==d1 && p1 == somaVetores p (dirVetorTroca d) = alteraCanhoesAux (i1+1) [DisparoCanhao i p d] t1 ++ alteraCanhoesAux (i1+1) t (Jogador p1 d1 v l c:t1)
                                                                                                             | v>0 && i==i1 && p1 /= somaVetores p (dirVetorTroca d) && dist (somaVetores p (direcaoParaVetor d)) p1 <= 2 = alteraCanhoesAux i1 t (Jogador p1 d1 v l c:t1)
                                                                                                             | v>0 && i/=i1 && dist (somaVetores p (direcaoParaVetor d)) p1 > 2 = alteraCanhoesAux (i1+1) [DisparoCanhao i p d] t1 ++ alteraCanhoesAux (i1+1) t (Jogador p1 d1 v l c:t1)
                                                                                                             | otherwise = alteraCanhoesAux (i1+1) [DisparoCanhao i p d] t1 ++ alteraCanhoesAux (i1+1) t (Jogador p1 d1 v l c:t1)
                                                                                          where dist (x1,y1) (x2,y2) = (x1-x2)^2 + (y1-y2)^2
alteraCanhoes (_:t) js = alteraCanhoes t js



-- | Funçao que move cada disparo de uma lista para a posiçao seguinte consoante a sua direçao.

alteraCanhoes1 :: [Disparo] -> [Disparo]
alteraCanhoes1 [] = []
alteraCanhoes1 (DisparoCanhao i p d:t) = DisparoCanhao i (somaVetores p (direcaoParaVetor d)) d : alteraCanhoes1 t
alteraCanhoes1 (_:t) = alteraCanhoes1 t

-- | Funçao que altera uma lista de 'Jogador' mediante a condiçao de terem sido afetados por um ou mais 'DisparoCanhao'.

jogCanhoes :: [Jogador] -> [Disparo] -> [Jogador]
jogCanhoes [] ds = []
jogCanhoes js [] = js
jogCanhoes (Jogador p1 d1 v l c:t) ds = jogCanhoesAux 0 (Jogador p1 d1 v l c:t) ds
                                  where jogCanhoesAux i1 [] [] = []
                                        jogCanhoesAux i1 [] ds = []
                                        jogCanhoesAux i1 js [] = js
                                        jogCanhoesAux i1 (Jogador p1 d1 v l c:t1) (DisparoCanhao i p d:t) | v>0 && i/=i1 && dist (somaVetores p (direcaoParaVetor d)) p1 <= 2 = jogCanhoesAux i [Jogador p1 d1 (v-1) l c] t ++ jogCanhoesAux (i+1) t1 (DisparoCanhao i p d:t)
                                                                                                          | v>0 && i==i1 && d==d1 && p1 == somaVetores p (dirVetorTroca d) = jogCanhoesAux i [Jogador p1 d1 v l c] t ++ jogCanhoesAux (i+1) t1 (DisparoCanhao i p d:t)
                                                                                                          | v>0 && i==i1 && p1 /= somaVetores p (dirVetorTroca d) && dist (somaVetores p (direcaoParaVetor d)) p1 <= 2 = jogCanhoesAux i [Jogador p1 d1 (v-1) l c] t ++ jogCanhoesAux (i+1) t1 (DisparoCanhao i p d:t)
                                                                                                          | v>0 && i/=i1 && dist (somaVetores p (direcaoParaVetor d)) p1 >= 2 = jogCanhoesAux i [Jogador p1 d1 v l c] t ++ jogCanhoesAux (i+1) t1 (DisparoCanhao i p d:t)
                                                                                                          | otherwise = Jogador p1 d1 v l c : jogCanhoesAux (i+1) t1 (DisparoCanhao i p d:t)
                                                                                          where dist (x1,y1) (x2,y2) = (x1-x2)^2 + (y1-y2)^2

-- | Funçao que altera uma lista de disparos 'Canhao' mediante a condiçao de terem colidido com outros disparos 'Canhao'.

dispCanhoes :: [Disparo] -> [Disparo]
dispCanhoes [] = []
dispCanhoes (DisparoCanhao i p d:t) | not (any (\ x -> posicaoDisparo x == p) t) = DisparoCanhao i p d : dispCanhoes t
                                    | otherwise = dispCanhoes (t \\ filter (\ x -> posicaoDisparo x == p) t)


-- | Funcao que recebe uma 'Direcao' e devolve um vetor unitário da 'Direcao' oposta.

dirVetorTroca :: Direcao -> PosicaoGrelha
dirVetorTroca C = direcaoParaVetor B
dirVetorTroca B = direcaoParaVetor C
dirVetorTroca E = direcaoParaVetor D
dirVetorTroca D = direcaoParaVetor E

-- | Funçao que altera um 'Mapa' se um 'DisparoCanhao' atingiu uma parede Destrutivel.

mapaCanhoes :: Mapa -> [Disparo] -> Mapa
mapaCanhoes m [] = m
mapaCanhoes m (DisparoCanhao i (y,x) d:t) | testaParedeDestrutivel (y,x) d m && d==C = atualizaPosicaoMatriz (y,x) Vazia (atualizaPosicaoMatriz (y,x+1) Vazia m)  
                                          | testaParedeDestrutivel (y,x) d m && d==B = atualizaPosicaoMatriz (y+1,x) Vazia (atualizaPosicaoMatriz (y+1,x+1) Vazia m)
                                          | testaParedeDestrutivel (y,x) d m && d==E = atualizaPosicaoMatriz (y,x) Vazia (atualizaPosicaoMatriz (y+1,x) Vazia m)
                                          | testaParedeDestrutivel (y,x) d m && d==D = atualizaPosicaoMatriz (y,x+1) Vazia (atualizaPosicaoMatriz (y+1,x+1) Vazia m)
                                          | otherwise = m
mapaCanhoes m (_:t) = mapaCanhoes m t

-- | Funçao que altera uma lista de 'DisparoCanhao' mediante a condiçao de ter atingido uma 'Parede'.

mapaCanhoes1 :: Mapa -> [Disparo] -> [Disparo]
mapaCanhoes1 m [] = []
mapaCanhoes1 m (DisparoCanhao i (y,x) d:t) | testaParedeDestrutivel (y,x) d m = mapaCanhoes1 m t
                                           | testaParedeIndestrutivel (y,x) d m = mapaCanhoes1 m t
                                           | otherwise = DisparoCanhao i (y,x) d : mapaCanhoes1 m t
mapaCanhoes1 m (_:t) = mapaCanhoes1 m t

-- | Função que testa se existe uma 'Parede' 'Indestrutivel' nas respetivas 'Posicao' correspondentes a uma 'PosicaoGrelha'

testaParedeIndestrutivel :: PosicaoGrelha -> Direcao -> Mapa -> Bool
testaParedeIndestrutivel (y,x) d m | d==C = p1 == Bloco Indestrutivel || p2 == Bloco Indestrutivel
                                   | d==B = p3 == Bloco Indestrutivel || p4 == Bloco Indestrutivel
                                   | d==E = p1 == Bloco Indestrutivel || p3 == Bloco Indestrutivel
                                   | d==D = p2 == Bloco Indestrutivel || p4 == Bloco Indestrutivel
                where p1 = encontraPosicaoMatriz (y,x) m
                      p2 = encontraPosicaoMatriz (y,x+1) m
                      p3 = encontraPosicaoMatriz (y+1,x) m
                      p4 = encontraPosicaoMatriz (y+1,x+1) m


-- | Função que testa se existe uma 'Parede' 'Destrutivel' nas respetivas 'Posicao' correspondentes a uma 'PosicaoGrelha'

testaParedeDestrutivel :: PosicaoGrelha -> Direcao -> Mapa -> Bool
testaParedeDestrutivel (y,x) d m | d==C = p1 == Bloco Destrutivel || p2 == Bloco Destrutivel
                                 | d==B = p3 == Bloco Destrutivel || p4 == Bloco Destrutivel
                                 | d==E = p1 == Bloco Destrutivel || p3 == Bloco Destrutivel
                                 | d==D = p2 == Bloco Destrutivel || p4 == Bloco Destrutivel
                where p1 = encontraPosicaoMatriz (y,x) m
                      p2 = encontraPosicaoMatriz (y,x+1) m
                      p3 = encontraPosicaoMatriz (y+1,x) m
                      p4 = encontraPosicaoMatriz (y+1,x+1) m

-- | Avança o 'Estado' do jogo um 'Tick' de tempo, considerando apenas os efeitos dos campos de 'Choque' disparados.
tickChoques :: Estado  -> Estado
tickChoques (Estado m js ds) = Estado m js (tickChoquesAux ds)

-- | Função que numa lista de 'Disparo' altera apenas os 'DisparoChoque' considerando o avanço de um 'Tick' de tempo

tickChoquesAux :: [Disparo] -> [Disparo]
tickChoquesAux [] = []
tickChoquesAux (DisparoChoque jd td:t) | td > 0 = DisparoChoque jd (td-1) : tickChoquesAux t
                                       | otherwise = tickChoquesAux t
tickChoquesAux (DisparoCanhao i p d:t) = DisparoCanhao i p d : tickChoquesAux t
tickChoquesAux (DisparoLaser i p d:t) = DisparoLaser i p d : tickChoquesAux t

