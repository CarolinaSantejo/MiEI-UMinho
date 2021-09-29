

data Semaforo = Verde | Amarelo | Vermelho deriving Show

intersecaoOk :: [Semaforo] -> Bool
intersecaoOk l = intersAux l False
        where intersAux :: [Semaforo] -> Bool -> Bool
              intersAux [] _ = True
              intersAux (Vermelho:t) v = intersAux t v
              intersAux ( _ :t) False = intersAux t True
              intersAux ( _ :t) True = False