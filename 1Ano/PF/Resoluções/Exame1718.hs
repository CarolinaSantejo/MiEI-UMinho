
-- 1

(!!!) :: [a] -> Int -> a
(!!!) (h:t) 0 = h
(!!!) (h:t) x = (!!!) t (x-1)

-- 2

data Movimento = Norte | Sul | Este | Oeste deriving Show

posicao :: (Int,Int) -> [Movimento] -> (Int,Int)
posicao (x,y) [] = (x,y)
posicao (x,y) (Norte:t) = posicao (x,y+1) t
posicao (x,y) (Sul:t) = posicao (x,y-1) t
posicao (x,y) (Este:t) = posicao (x+1,y) t
posicao (x,y) (Oeste:t) = posicao (x-1,y) t

-- 3

any' :: (a -> Bool) -> [a] -> Bool
any' f [] = False
any' f (h:t) = f h || any' f t

-- 4

type Mat a = [[a]]

triSup :: Eq a => Num a => Mat a -> Bool
triSup (h:t) | length (h:t) > 1 && (head h) /= 0 = (all (==0) (map head t))  && triSup (map tail t)
             | length (h:t) == 1 && (head h) == 0 = False
             | otherwise = True


-- 5

movimenta :: IO (Int,Int)
movimenta = movimentaAux (0,0)

movimentaAux :: (Int,Int) -> IO (Int,Int)
movimentaAux (x,y) = do putStrLn "Indique um movimento (N, S, E ou O)"
                        d <- getChar
                        if (d == 'N') then movimentaAux (x,y+1)
                          else 
                              if (d == 'S') then movimentaAux (x,y-1)
                                else
                                    if (d == 'E') then movimentaAux (x+1,y)
                                      else
                                          if (d == 'O') then movimentaAux (x-1,y)
                                            else return (x,y)

-- 6

data Imagem = Quadrado Int
            | Mover (Int,Int) Imagem
            | Juntar [Imagem]

ex = Mover (5,5)
      (Juntar [Mover (0,1) (Quadrado 5),
              Quadrado 4,
              Mover (4,3) (Quadrado 2)])


-- (a)

vazia :: Imagem -> Bool
vazia (Quadrado l) = False
vazia (Mover (x,y) im) = vazia im
vazia (Juntar []) = True
vazia (Juntar (h:t)) = vazia h && vazia (Juntar t)

-- (b)

maior :: Imagem -> Maybe Int
maior (Quadrado l) = Just l
maior (Mover (x,y) im) = maior im
maior (Juntar []) = Nothing
maior (Juntar [Quadrado l]) = Just l
maior (Juntar ((Mover (x,y) im):t)) = maior (Juntar (im : t))
maior (Juntar ((Juntar l):t)) = maior (Juntar (l ++ t))
maior (Juntar ((Quadrado l1):(Quadrado l2):t)) | l1 > l2 = maior (Juntar ((Quadrado l1):t))
                                               | otherwise = maior (Juntar ((Quadrado l2):t))
maior (Juntar ((Quadrado l1):(Mover p im):t)) = maior (Juntar ((Quadrado l1):im:t))
maior (Juntar ((Quadrado l1):(Juntar l):t)) = maior (Juntar ((Juntar ((Quadrado l1):l)):t))

-- (c)


{-
instance Eq Imagem where
	(Quadrado l1) == (Quadrado l2) = l1 == l2
	(Quadrado l1) /= (Quadrado l2) = l1 /= l2
	(Mover (x1,y1) im1) == (Mover (x2,y2) im2) | x1 == x2 && y1 == y2 = im1 == im2

-}