
--1

data ExpInt = Const Int 
            | Simetrico ExpInt
            | Mais ExpInt ExpInt
            | Menos ExpInt ExpInt
            | Mult ExpInt ExpInt
    deriving Show
 --(a)

calcula :: ExpInt -> Int
calcula (Const i) = i
calcula (Mais e1 e2) = calcula e1 + calcula e2
calcula (Menos e1 e2) = calcula e1 - calcula e2
calcula (Simetrico e) = - (calcula e)

--(b)

infixa :: ExpInt -> String
infixa (Const i) = show i
infixa (Mais e1 e2) = "(" ++ (infixa e1) ++ "+" ++ (show (infixa e2)) ++ ")"
infixa (Menos e1 e2) = "(" ++ (show (infixa e1)) ++ "-" ++ (show (infixa e2)) ++ ")"
infixa (Simetrico e) = "-(" ++ (show (infixa e)) ++ ")"


--2

data RTree a = R a [RTree a]

--(a)

soma :: Num a => RTree a -> a
soma (R a l) = a + sum (map soma l)

--OU
soma' :: Num a => RTree a -> a
soma' (R a l) = a + foldr (\h r -> soma h + r) 0 l


--(b)

altura :: RTree a -> Int
altura (R a []) = 1
altura (R a l) = 1 + maximum (map altura l)

--(c)
prune :: Int -> RTree a -> RTree a
prune 0 (R a _ ) = R a []
prune i (R a l) = R a (map (prune (i-1) l))


--(d)

mirror :: RTree a -> RTree a
mirror (R a l) = R a (reverse (map mirror l))