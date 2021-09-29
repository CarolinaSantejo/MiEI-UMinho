{-|

== Implementação do Jogo em Gloss

=== Trabalho realizado por: 
    Bernardo Maria Cortez Marques Nogueira da Graça A84898
    Carolina Gil Afonso Santejo A89500

==== Introdução

Esta tarefa pertence ao trabalho enquadrado na cadeira de LI1 do curso de Mestrado Integrado em Engenharia Informática da Universidade do Minho. 
Nesta foi nos pedido para implementar o jogo completo utilizando a Biblioteca Gloss. 
Esta tarefa não foi bem resolvida e o resultado final está bastante longe daquilo que pretendiamos.


==== Descrição do problema

Após ter definido as jogadas e as consequências dos vários disparos nas tarefas 2 e 4 do projeto,
a fase seguinte foi aplicar esses pontos de um modo visual, que foi o objetivo desta tarefa.
O resultado desta tarefa depende então da qualidade das tarefas mencionadas neste parágrafo.


==== Aparelhagem e Equipamento 

Esta tarefa foi realizada no sistema operativo Ubuntu da Linux e o codigo e comentários foram escritos usando
o programa Sublime. As imagens foram editadas utilizando o software da Adobe chamado Photoshop.


==== Procedimento

O primeiro passo foi fazer um jogo mais simplista possivel. Começamos por definir os dados gerais do jogo,
como o frame rate, a janela, o estado do jogo, entre outros.
Após este passo tratamos de todas as imagens, e tentamos que fossem todas simples e ao mesmo tempo apelativas.
Adotamos um estilo "8 bit" para dar um efeito retro ao jogo.
A ultima coisa feita foi adicionar as imagems ao jogo e colocar o tanque a movimentar-se pela janela do jogo com as setas do teclado.


==== Análise dos resultados
Esta tarefa ficou excessivamente simplista, o jogo em si está bastante diferente do objetivo traçado neste desafio.
As várias funções utilizadas nesta tarefa resultam numa janela muito simples só com um tanque.
Assim podemos dizer que esta tarefa não foi bem sucedida.


==== Conclusões 
Ao contrário de todas as outras tarefas, o resultado final desta ficou bastante distante do resultado pedido. O código presente é bastante minimalista e não entrega nada a mais do que aquilo que está presente nos ficheiros de apoio no site da cadeira. 
Por isso não há muito que se possa a dizer sobre esta parte do trabalho.

-}
module Tarefa5_2018li1g085 where

import LI11819
import Graphics.Gloss
import Graphics.Gloss.Interface.Pure.Game
import Graphics.Gloss.Data.Bitmap

-- * Função principal da Tarefa 5.
main :: IO ()
main = do t1 <- loadBMP "tank1.bmp"
          t2 <- loadBMP "tank2.bmp"
          t3 <- loadBMP "tank3.bmp"
          t4 <- loadBMP "tank4.bmp"
          ind <- loadBMP "indestrutivel.bmp"
          des <- loadBMP "destrutivel.bmp"
          la <- loadBMP "laser.bmp"
          bl <- loadBMP "bala.bmp"
          ch <- loadBMP "choque.bmp"
          fl <- loadBMP "floor.bmp"
          play dm
               (greyN 0.5)
               fr
               (estadoInicialGloss t1)
               desenhaEstado
               reageEvento
               reageTempo

-- * Funções constituintes da função principal

-- | Definiçao do estado no jogo 
type StateGloss = (Float,Float,Picture)

-- | Funçao que junta uma matriz de Pictures numa lista de Pictures
juntaPictures :: [[Picture]] -> [Picture]
juntaPictures [[]] = []
juntaPictures (x:xs) = (juntaPicture x:juntaPictures xs)

-- | Funçao que junta uma lista de Pictures numa só Picture
juntaPicture :: [Picture] -> Picture
juntaPicture p = Pictures p

-- | Funçao que junta uma matriz de Pictures numa só Picture	
juntaGeral :: [[Picture]] -> Picture
juntaGeral p = juntaPicture (juntaPictures p)

-- | Estado inicial do Jogo
estadoInicialGloss :: Picture -> StateGloss
estadoInicialGloss p = (0,0,p)

desenhaEstado :: StateGloss -> Picture
desenhaEstado (x,y,p) = Translate x y p 

-- |Funçao inicial de reaçao a eventos do jogo
reageEvento :: Event -> StateGloss -> StateGloss
reageEvento (EventKey (SpecialKey KeyUp)    Down _ _) (x,y,p) = (x,y+5,p)
reageEvento (EventKey (SpecialKey KeyDown)  Down _ _) (x,y,p) = (x,y-5,p)
reageEvento (EventKey (SpecialKey KeyLeft)  Down _ _) (x,y,p) = (x-5,y,p)
reageEvento (EventKey (SpecialKey KeyRight) Down _ _) (x,y,p) = (x+5,y,p)
reageEvento _ s = s

-- | Funçao inicial de reaçao ao tempo no jogo
reageTempo :: Float -> StateGloss -> StateGloss
reageTempo n (x,y,p) = (x,y,p)

-- | Frame rate do jogo
fr :: Int
fr = 50

-- | Janela do jogo
dm :: Display
dm = InWindow "Novo Jogo" (1000,1000) (0,0)

