{- |
Este módulo define funções comuns da Tarefa 3 do trabalho prático.

* __Autores__: A89500 - Carolina Gil Afonso Santejo; A84898 - Bernardo Maria Cortez Marques Nogueira da Graça

* __Grupo__: 2018li1g085 


* __Data__: 28 de Dezembro de 2018 


* __Docente__: João Alexandre Baptista Vieira Saraiva


= Introdução

Este documento foi a terceira tarefa fornecida aos alunos pelos docentes da disciplina de Laboratórios de Informática I no âmbito de 
avaliar as competências desenvolvidas ao longo do primeiro semestre do primeiro ano do curso Mestrado Integrado em Engenharia Informática na Universidade do Minho. 
Esta Tarefa em particular, deu liberdade aos alunos de se desafiarem entre si, na medida em que foi fornecida uma classificação instantânea quantitativa 
do desempenho de cada grupo. Esta classificação basea-se no cálculo da taxa de compressão obtida pela função 'comprime' que tem como objetivo reduzir o espaço ocupado por um 'Estado' durante os momentos em que o Jogador não está a jogar o jogo.
Para a realização deste trabalho foram utilizados três programas em simultâneo: Sublime Text, Haskell e Haddock. O primeiro teve a finalidade de editar todo o código para depois ser executado pelo compilador 
e por fim o Haddock teve como função melhorar a compreensão de todo o código escrito.
O principal problema a resolver nesta tarefa é conseguir a redução ao máximo de carateres utilizados para descrever um 'Estado' de modo a que seja possivel voltar à denominação original utilizando a função 'descomprime'.

= Objetivos 

O objetivo desta tarefa é, dada uma descrição do estado do jogo implementar um mecanismo de compressão / descompressão que permita poupar
carateres e, desta forma, poupar espaço em disco quando o estado do jogo for gravado
(permitindo, por exemplo, fazer pausa durante o jogo com o objetivo de o retomar mais tarde).
A primeira estratégia utilizada consistiu na designação de cada elemento diferente por apenas uma letra o que facilitou 
a leitura principalmente de 'Mapa's que fossem muito extensos.
Por ultimo foram eliminadas redundâncias que não fossem necessárias à interpretação de um 'Estado' como por exemplo a palavra Estado no início de cada 'Estado'.
Por fim e não menos importante foi utilizada a função pré definida 'read' na função principal 'descomprime' para reverter o 'Estado' para designação inicial.

= Conclusão

Em suma, com a realização deste trabalho foram obtidos resultados razoáveis no que diz respeito às classificações dos restantes grupos avaliados.
Com uma taxa de compressão de 91.13% foi alcançado o objetivo proposto pelos docentes 

-}

module Tarefa3_2018li1g085 where

import LI11819
import Data.Char
import Tarefa1_2018li1g085

-- * Testes

-- | Testes unitários da Tarefa 3.
--
-- Cada teste é um 'Estado'.
testesT3 :: [Estado]
testesT3 = [Estado m j1 d1,Estado m j2 d1,Estado m j3 d1,Estado m j1 d2,Estado m j1 d3,Estado m j1 d4,Estado m j2 d2,Estado m j2 d3,Estado m j2 d4,Estado m j3 d2,Estado m j3 d3,Estado m j3 d4,Estado m j4 d4,Estado m j5 d3,Estado m1 j5 d3]
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
                     j1 = [Jogador (1,1) D 5 3 3, Jogador (4,2) C 4 0 2, Jogador (5,6) B 5 3 2, Jogador (1,6) D 5 0 2]
                     j2 = [Jogador (1,1) D 5 3 3, Jogador (1,3) C 4 1 2, Jogador (4,6) B 5 3 2, Jogador (2,7) D 5 0 2]
                     j3 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2, Jogador (5,6) B 5 3 2, Jogador (7,2) D 5 0 0]
                     j4 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2, Jogador (7,2) C 4 0 2]
                     j5 = [Jogador (7,6) D 5 3 3, Jogador (1,4) C 4 0 2]
                     d1 = [DisparoChoque 0 5, DisparoLaser 1 (5,4) C, DisparoChoque 1 5]
                     d2 = [DisparoChoque 3 5, DisparoLaser 1 (3,4) C, DisparoCanhao 2 (6,6) E]
                     d3 = [DisparoChoque 1 5, DisparoLaser 0(5,4) C, DisparoLaser 1 (5,5) E]
                     d4 = [DisparoChoque 1 5, DisparoLaser 2 (7,7) C, DisparoLaser 0 (7,2) B]




-- * Funções principais da Tarefa 3.
-- * Compressão
-- | Comprime um 'Estado' para formato textual.
--
-- __NB:__ A função 'show' representa um 'Estado' num formato textual facilmente legível mas extenso.
--
-- __NB:__ Uma boa solução deve representar o 'Estado' dado no mínimo número de caracteres possível.

comprime :: Estado -> String
comprime (Estado m js ds) = compM m ++ compJ js ++ compD ds

-- | Função que comprime um 'Mapa' para formato textual

compM :: Mapa -> String
compM [] = ""
compM [a] = comp a
compM (h:t) = comp h ++ "|" ++ compM t

-- | Função que comprime uma linha do 'Mapa' para formato textual

comp :: [Peca] -> String
comp [] = ""
comp (h:t) | h == Vazia = 'V' : comp t 
           | h == Bloco Indestrutivel = 'I' : comp t 
           | otherwise = 'D' : comp t 

-- | Função que comprime uma lista de 'Jogador'es para formato textual

compJ :: [Jogador] -> String
compJ [] = ""
compJ (Jogador p d v l c :t) = "J" ++ show p ++ show d ++ show v ++ "|" ++ show l ++ "|" ++ show c ++ compJ t

-- | Função que comprime uma lista de 'Disparo's para formato textual

compD :: [Disparo] -> String
compD [] = ""
compD (h:t) = comp h ++ compD t
           where comp (DisparoLaser j p d) = "L" ++ show j ++ show p ++ show d
                 comp (DisparoCanhao j p d) = "N" ++ show j ++ show p ++ show d
                 comp (DisparoChoque j tp) = "Q" ++ show j ++ show tp

-- * Descompressão
-- | Descomprime um 'Estado' no formato textual utilizado pela função 'comprime'.
--
-- __NB:__ A função 'comprime' é válida de for possível recuperar o 'Estado' utilizando a função 'descomprime', i.e.:
--
-- prop> descomprime . comprime = id
--
-- __NB:__ Esta propriedade é particularmente válida para a solução pré-definida:
--
-- prop> read . show = id

descomprime :: String -> Estado
descomprime x = Estado (descompM (takeWhile mapaStr x)) (descompJ (dropWhile mapaStr x)) (descompD (dropWhile mapaStr x))

-- | Função que descomprime um mapa

descompM :: String -> Mapa
descompM "" = []
descompM m = dcpL (takeWhile lStr m) : descompM (drop 1 (dropWhile lStr m)) 

-- | Função que descomprime um formato textual para formar uma linha do mapa

dcpL :: String -> [Peca]
dcpL "" = []
dcpL ('V':t) = Vazia : dcpL t
dcpL ('I':t) = Bloco Indestrutivel : dcpL t
dcpL ('D':t) = Bloco Destrutivel : dcpL t

-- | Função que testa se um formato textual representa as peças de um 'Mapa'

lStr :: Char -> Bool
lStr h = h =='V' || h == 'D' || h == 'I'

-- | Função que testa se um formato textual representa um 'Mapa'

mapaStr :: Char -> Bool
mapaStr h = h =='V' || h == 'D' || h == 'I' || h == '|'

-- | Função que testa se um formato textual representa uma parte da 'Posicao'

posStr :: Char -> Bool
posStr h = h=='(' || isDigit h || h==','


-- | Função que descomprime um formato textual que representa uma lista de jogadores

descompJ :: String -> [Jogador]
descompJ "" = []
descompJ ('J':t) = Jogador (read tiraPos :: Posicao) (read tiraD :: Direcao) (read tiraV :: Int) (read tiraL :: Int) (read tiraC :: Int) : descompJ tiraJog 
    where tiraPos = takeWhile posStr t ++ ")"
          tiraD = take 1 (drop 1 (dropWhile posStr t))
          tiraV = takeWhile isDigit (drop 2 (dropWhile posStr t))
          tiraL = takeWhile isDigit (drop 1 (dropWhile isDigit (drop 2 (dropWhile posStr t))))
          tiraC = takeWhile isDigit (drop 1 (dropWhile isDigit (drop 1 (dropWhile isDigit (drop 2 (dropWhile posStr t))))))
          tiraJog = dropWhile isDigit (drop 1 (dropWhile isDigit (drop 1 (dropWhile isDigit (drop 2 (dropWhile posStr t))))))
descompJ ( _ :t) = []



-- | Função que descomprime um formato textual que representa uma lista de disparos


descompD :: String -> [Disparo]
descompD "" = []
descompD ('L':t) = DisparoLaser (read (take 1 t)) (read tiraPos :: Posicao) (read tiraD :: Direcao) : descompD tiraJog
          where tiraPos = takeWhile posStr (drop 1 t) ++ ")"
                tiraD = take 1 (drop 1 (dropWhile posStr t))
                tiraJog = drop 2 (dropWhile posStr t)

descompD ('N':t) = DisparoCanhao (read (take 1 t)) (read tiraPos :: Posicao) (read tiraD :: Direcao) : descompD tiraJog
          where tiraPos = takeWhile posStr (drop 1 t) ++ ")"
                tiraD = take 1 (drop 1 (dropWhile posStr t))
                tiraJog = drop 2 (dropWhile posStr t)

descompD ('Q':t) = DisparoChoque (read (take 1 t) :: Int) (read (take 1 (drop 1 t)) :: Int) : descompD (drop 2 t)
descompD ( _ :t) = descompD t


{- |

-}