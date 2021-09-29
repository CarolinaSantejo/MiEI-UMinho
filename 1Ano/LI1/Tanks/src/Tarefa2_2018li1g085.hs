-- | Este módulo define funções comuns da Tarefa 2 do trabalho prático.
module Tarefa2_2018li1g085 where

import LI11819
import Tarefa0_2018li1g085
import Tarefa1_2018li1g085


-- * Testes

-- | Testes unitários da Tarefa 2.
--
-- Cada teste é um triplo (/identificador do 'Jogador'/,/'Jogada' a efetuar/,/'Estado' anterior/).
testesT2 :: [(Int,Jogada,Estado)]
testesT2 = [(3,Dispara Choque, Estado m j1 d1),(0,Dispara Laser, Estado m j2 d2),(0,Dispara Choque, Estado m j1 d3),(2,Dispara Canhao, Estado m j2 d4),(1,Dispara Choque, Estado m j1 d3),(3,Dispara Laser, Estado m j2 d1),(3,Movimenta C, Estado m j1 d1),(1,Movimenta B, Estado m j2 d2),(2,Movimenta E, Estado m j3 d2),(3,Movimenta D, Estado m j3 d3),(0,Movimenta D, Estado m j1 d4),(3,Dispara Laser, Estado m j3 d3),(2,Movimenta B, Estado m j2 d3)]
      where m = mapaInicial (10,10)
            j1 = [Jogador (1,1) D 5 3 3, Jogador (4,2) C 4 0 2, Jogador (5,6) B 5 3 2, Jogador (1,6) D 5 0 2]
            j2 = [Jogador (1,1) D 5 3 3, Jogador (1,3) C 4 1 2, Jogador (4,6) B 5 3 2, Jogador (2,7) D 5 0 2]
            j3 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2, Jogador (5,6) B 5 3 2, Jogador (7,2) D 5 0 0]
            d1 = [DisparoChoque 0 5, DisparoLaser 1 (5,4) C, DisparoChoque 1 5]
            d2 = [DisparoChoque 3 5, DisparoLaser 1 (3,4) C, DisparoCanhao 2 (6,6) E]
            d3 = [DisparoChoque 2 5, DisparoLaser 2 (5,4) C, DisparoLaser 2 (5,5) E]
            d4 = [DisparoChoque 1 5, DisparoLaser 3 (7,7) C, DisparoLaser 0 (7,2) B]




-- * Funções principais da Tarefa 2.

-- | Efetua uma jogada.
jogada :: Int -- ^ O identificador do 'Jogador' que efetua a jogada.
       -> Jogada -- ^ A 'Jogada' a efetuar.
       -> Estado -- ^ O 'Estado' anterior.
       -> Estado -- ^ O 'Estado' resultante após o jogador efetuar a jogada.

jogada i (Dispara Canhao) (Estado m js ds) | v>0 = Estado m js (DisparoCanhao i ps d :ds) 
                                           | otherwise = Estado m js ds
                    where (Jogador p d v l c) = encontraIndiceLista i js
                          ps = somaVetores p (direcaoParaVetor d)

jogada i (Dispara Laser) (Estado m js ds) | l>0 && v>0 = Estado m (atualizaIndiceLista i (Jogador p d v (l-1) c) js) (DisparoLaser i ps d : ds) 
                                          | otherwise = Estado m js ds
                    where (Jogador p d v l c) = encontraIndiceLista i js
                          ps = somaVetores p (direcaoParaVetor d)


jogada i (Dispara Choque) (Estado m js ds) | c>0 && v>0 = Estado m (atualizaIndiceLista i (Jogador p d v l (c-1)) js) (DisparoChoque i 5 : ds) 
                                           | otherwise = Estado m js ds
                       where (Jogador p d v l c) = encontraIndiceLista i js
                          

jogada i (Movimenta dir) (Estado m js ds) | v>0 && (d==dir) && testaChoque i (Estado m js ds) && testaTanque (encontraIndiceLista i js) js && testaParede p d m = Estado m (atualizaIndiceLista i (Jogador (somaVetores p (direcaoParaVetor dir)) d v l c) js) ds
                                          | v>0 && d/=dir = Estado m (atualizaIndiceLista i (Jogador p (rodaDirecaoPara d dir) v l c) js) ds
                                          | otherwise = Estado m js ds
                   where Jogador p d v l c = encontraIndiceLista i js
                         rodaDirecaoPara x y = y

-- | Função que testa se existem 'Parede's nas 'Posicao' que um 'Jogador' vai ocupar depois da sua movimentação

testaParede :: PosicaoGrelha -> Direcao -> Mapa -> Bool
testaParede (x,y) d m = p1 == Vazia && p2 == Vazia && p3 == Vazia && p4 == Vazia
                where p1 = encontraPosicaoMatriz (somaVetores (x,y) (direcaoParaVetor d)) m
                      p2 = encontraPosicaoMatriz (somaVetores (x+1,y) (direcaoParaVetor d)) m
                      p3 = encontraPosicaoMatriz (somaVetores (x,y+1) (direcaoParaVetor d)) m
                      p4 = encontraPosicaoMatriz (somaVetores (x+1,y+1) (direcaoParaVetor d)) m

-- | Função que testa se existe um jogador na mesma posição para onde o jogador selecionado se vai movimentar

testaTanque :: Jogador -> [Jogador] -> Bool
testaTanque (Jogador p d v l c) [] = True
testaTanque (Jogador p d v l c) (Jogador p1 d1 v1 l1 c1 : t) | p/=p1 && v1>0 = dist (somaVetores p (direcaoParaVetor d)) p1 > 2 && testaTanque (Jogador p d v l c) t
                                                             | otherwise = testaTanque (Jogador p d v l c) t                       
                                      where dist (x1,y1) (x2,y2) = (x1-x2)^2 + (y1-y2)^2 


-- | Função que testa se o jogador selecionado está a ser afetado por um choque de outro jogador

testaChoque :: Int -> Estado -> Bool
testaChoque i (Estado m js []) = True
testaChoque i (Estado m js (DisparoChoque x t:ds)) | i==x = testaChoque i (Estado m js ds)
                                                   | i/=x && (vidasJogador (encontraIndiceLista x js) > 0) = foraChoque i x  && testaChoque i (Estado m js ds)
                                                   | otherwise = testaChoque i (Estado m js ds)
                       where foraChoque i x = snd (posicaoJogador (encontraIndiceLista i js)) >= snd (posicaoJogador (encontraIndiceLista x js)) + 4 || fst (posicaoJogador (encontraIndiceLista i js)) >= fst (posicaoJogador (encontraIndiceLista x js)) + 4 || snd (posicaoJogador (encontraIndiceLista i js)) <= snd (posicaoJogador (encontraIndiceLista x js)) - 4 || fst (posicaoJogador (encontraIndiceLista i js)) <= fst (posicaoJogador (encontraIndiceLista x js)) - 4                
testaChoque i (Estado m js ( _ : ds)) = testaChoque i (Estado m js ds)

