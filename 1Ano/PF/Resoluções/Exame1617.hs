

--1 
--(a)

unlines' :: [String] -> String
unlines' [] = ""
unlines' [a] = a
unlines' (h:t) = h ++ "\n" ++ unlines' t

--(b)
(\\\) :: (Eq a) => [a] -> [a] -> [a]
(\\\) l [] = l
(\\\) [] l = []
(\\\) (h1:t1) (h2:t2) | h1 == h2 = (\\\) t1 t2
                      | otherwise = h1 : (\\\) t1 (h2:t2)

--2

data Seq a = Nil | Inicio a (Seq a) | Fim (Seq a) a deriving Show

--(a)

primeiro :: Seq a -> a
primeiro (Inicio a (Nil)) = a
primeiro (Fim (Nil) a) = a
primeiro (Inicio a seq) = a
primeiro (Fim seq a) = primeiro seq

--(b)

semUltimo :: Seq a -> Seq a
semUltimo (Inicio a (Nil)) = Nil
semUltimo (Fim (Nil) a) = Nil
semUltimo (Inicio a seq) = Inicio a (semUltimo seq)
semUltimo (Fim seq a) = seq

--3

data BTree a = Empty | Node a (BTree a) (BTree a) deriving Show

t :: BTree Int
t = Node 5 (Node 3 (Node 2 Empty Empty)
                   (Empty))
           (Empty)

--(a)

prune :: Int -> BTree a -> BTree a
prune i Empty = Empty
prune 0 t = Empty
prune i (Node v e d) = Node v (prune (i-1) e) (prune (i-1) d)

--(b)

semMinimo :: (Ord a) => BTree a -> BTree a
semMinimo Empty = Empty
semMinimo (Node v Empty d) = d
semMinimo (Node v e Empty) = Node v (semMinimo e) Empty 
semMinimo (Node v e d) = Node v (semMinimo e) d

--4
type Tabuleiro = [String]

exemplo :: Tabuleiro
exemplo = ["..R.",
           "R...",
           "...R",
           ".R.."]

--(a)

posicoes :: Tabuleiro -> [(Int,Int)]
posicoes l = pos 0 l
    where pos i [] = []
          pos i (h:t) = posAux (0,i) h ++ pos (i+1)  t


posAux :: (Int,Int) -> String -> [(Int,Int)]
posAux p [] = []
posAux (y,x) ('R':t) = (y,x) : posAux (y+1,x) t 
posAux (y,x) (_:t) = posAux (y+1,x) t


--(b)

valido :: Tabuleiro -> Bool
valido l = testaPos (posicoes l)

testaPos :: [(Int,Int)] -> Bool
testaPos [] = True
testaPos [p] = True
testaPos ((y1,x1):(y2,x2):t) = y1 /= y2 && x1 /= x2 && (x1+y1 /= x2+y2 || abs (x1-y1) /= abs (x2-y2)) && testaPos ((y1,x1):t) && testaPos ((y2,x2):t)

--(c)

bemFormado :: Int -> Tabuleiro -> Bool
bemFormado i [] = True
bemFormado i l = testaColunas i l && contaRainhas l == i && length l == i && (all (==True) (map (all f) l))

f :: Char -> Bool
f 'R' = True
f '.' = True
f _ = False

f1 :: Char -> Bool
f1 'R' = True
f1 _ = False

contaRainhas :: Tabuleiro -> Int
contaRainhas [] = 0
contaRainhas (h:t) = length (filter f1 h) + contaRainhas t

testaColunas :: Int -> Tabuleiro -> Bool
testaColunas i [] = True
testaColunas i (h:t) = length h == i && testaColunas i t