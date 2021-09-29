

data BTree a = Empty
             | Node a (BTree a) (BTree a) 


instance Show a => Show (BTree a) where
      show Empty = "."
      show (Node a e d) = "(" ++ show e ++ "<" ++ show a ++ ">" ++ show d ++ ")"


t :: BTree Int
t = Node 5 (Node 3 (Empty)
                   (Node 2 Empty Empty))
           (Empty)

--1

--(a)

altura :: BTree a -> Int
altura Empty = 0
altura (Node v e d) = 1 + max ae ad
       where ae = altura e
             ad = altura d

--(b)

contaNodos :: BTree a -> Int
contaNodos Empty = 0
contaNodos (Node v e d) = 1 + contaNodos e + contaNodos d


--(c)

folhas :: BTree a -> Int
folhas Empty = 0
folhas (Node _ Empty Empty) = 1
folhas (Node _ e d) = folhas e + folhas d

--(d)

prune :: Int -> BTree a -> BTree a
prune i Empty = Empty
prune 0 (Node v e d) = Empty
prune i (Node v e d) = Node v (prune (i-1) e) (prune (i-1) d)

-- (e)

path :: [Bool] -> BTree a -> [a]
path l Empty = []
path [] t = []
path (True:t) (Node v e d) = v : path t d
path (False:t) (Node v e d) = v : path t e

--(f)

mirror :: BTree a -> BTree a
mirror Empty = Empty
mirror (Node v e d) = Node v (mirror d) (mirror e)

--(g)

zipWithBT :: (a -> b -> c) -> BTree a -> BTree b -> BTree c
zipWithBT f Empty t = Empty
zipWithBT f t Empty = Empty
zipWithBT f (Node v1 e1 d1) (Node v2 e2 d2) = Node (f v1 v2) (zipWithBT f e1 e2) (zipWithBT f d1 d2)

--(h)

unzipBT :: BTree (a,b,c) -> (BTree a, BTree b, BTree c)
unzipBT Empty = (Empty,Empty,Empty)
unzipBT (Node (a,b,c) Empty Empty) = ((Node a Empty Empty),(Node b Empty Empty),(Node c Empty Empty))
unzipBT (Node (a,b,c) e d) = ((Node a (unzipA e) (unzipA d)),(Node b (unzipB e) (unzipB d)),(Node c (unzipC e) (unzipC d)))
                       where unzipA Empty = Empty
                             unzipA (Node (a,b,c) Empty Empty) = Node a Empty Empty
                             unzipA (Node (a,b,c) e d) = Node a (unzipA e) (unzipA d)
                             unzipB Empty = Empty
                             unzipB (Node (a,b,c) Empty Empty) = Node b Empty Empty
                             unzipB (Node (a,b,c) e d) = Node b (unzipB e) (unzipB d)
                             unzipC Empty = Empty
                             unzipC (Node (a,b,c) Empty Empty) = Node c Empty Empty
                             unzipC (Node (a,b,c) e d) = Node c (unzipC e) (unzipC d)
                             