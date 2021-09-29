

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr'f b [] = b
foldr' f b (h:t) = f h (foldr' f b t)

sum' l = foldr' (+) 0 l

mult' l = foldr' (*) 1 l 

anyBool :: [Bool] -> Bool
anyBool [] = False
anyBool l = foldr' (||) False l

all'' :: (a -> Bool) -> [a] -> Bool
all'' p [] = True
all'' p (h:t) = (&&) (p h) (all p t)

--OU

--all' l = foldr' f True l

--f :: a -> Bool -> Bool
--f h r = (&&) (f h) r

-- ----------

conta :: Int -> Polinomio -> Int
conta i [] = 0
conta i ((c,e):t) = if i==e then 1 + conta i t
                            else conta i t

--OU

conta' l = foldr g 0 l 

g :: Monomio -> Int -> Int
g (c,e) r = if e==r then 1+r else r


--1

any' :: (a -> Bool) -> [a] -> Bool
any' p [] = False
any' p (h:t) = (p h) || (any' p t)


zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' f [] _ = []
zipWith' f _ [] = []
zipWith' f (h1:t1) (h2:t2) = (f h1 h2) : zipWith' f t1 t2


takeWhile' :: (a -> Bool) -> [a] -> [a]
takeWhile' f [] = []
takeWhile' f (h:t) | f h = h : takeWhile' f t
                   | otherwise = [] 


dropWhile' :: (a -> Bool) -> [a] -> [a]
dropWhile' f [] = []
dropWhile' f (h:t) | f h = dropWhile' f t
                   | otherwise = (h:t)


span' :: (a -> Bool) -> [a] -> ([a],[a])
span' p [] = ([],[])
span' p (h:t) = if p h then (h:lt , ld) else ([] , (h:t))
                where (lt,ld) = span' p t

deleteBy :: (a -> a -> Bool) -> a -> [a] -> [a]
deleteBy f a [] = []
deleteBy f a (h:t) | f a h = t
                   | otherwise = h : deleteBy f a t

sortOn :: Ord b => (a -> b) -> [a] -> [a]
sortOn f [] = []
sortOn f [a] = [a]
sortOn f (h1:h2:t) | f h1 <= f h2 = h1 : sortOn f (h2:t)
                   | otherwise = h2 : sortOn f (h1:t)



--2

type Polinomio = [Monomio]
type Monomio = (Float , Int)

selgrau :: Int -> Polinomio -> Polinomio
selgrau i pol = filter f pol
     where f (c,g) = g==i

selgrau' :: Int -> Polinomio -> Polinomio
selgrau' i p = foldr f [] p 
       where f (c,e) r = if e==i then (c,e):r else r


grau p = foldr f (-1)  p
   where f :: Monomio -> Int -> Int
         f (c,e) r = if e>r then e else r


deriv :: Polinomio -> Polinomio
deriv pol = map derivMonomio pol

derivMonomio :: Monomio -> Monomio
derivMonomio (c,g) = (c*(fromIntegral g) , g-1)


simp :: Polinomio -> Polinomio
simp pol = filter eGrau0 pol 

eGrau0 :: Monomio -> Bool
eGrau0 (x,y) = (y/=0)


calcula :: Float -> Polinomio -> Float
calcula p [] = 0
calcula p ((x,g):t) = x*(p^g) + calcula p t


--OU 

calcula' p l = foldr f 0 l
   where f :: Monomio -> Float -> Float 
         f (x,g) t = x*(p^g) + t


--3

type Mat a = [[a]]

-- (a)

dimOK :: Mat a -> Bool
dimOK [] = True
dimOK [a] = True
dimOK (h1:h2:t) = (length h1) == (length h2) && dimOK (h2:t)

-- (b)

dimMat :: Mat a -> (Int,Int)
dimMat (h:t) = (length (h:t), length h)

-- (c)

addMat :: Num a => Mat a -> Mat a -> Mat a
addMat l [] = l
addMat [] l = l
addMat (h1:t1) (h2:t2) = zipWith (+) h1 h2 : addMat t1 t2  

-- (d)

transpose :: Mat a -> Mat a
transpose [[]] = []
transpose l | length (head l) > 0 = map head l : transpose (map (drop 1) l) 
            | otherwise = []

-- (e) ??

multMat :: Num a => Mat a -> Mat a -> Mat a
multMat [[]] _ = []
multMat _ [[]] = []
multMat l1 l2 | length l1 > 0 && length l2 > 0 = [sum (zipWith (*) (head l1) (map head l2))] : multMat (drop 1 l1) (map (drop 1) l2)
              | otherwise = []

-- (f)

zipWMat :: (a -> b -> c) -> Mat a -> Mat b -> Mat c
zipWMat f m1 m2 | length m1 > 0 && length m2 > 0 = zipWith f (head m1) (head m2) : zipWMat f (drop 1 m1) (drop 1 m2)
                | otherwise = []

-- (g)

triSup :: Eq a => Num a => Mat a -> Bool
triSup [] = True
triSup (h:t) | length h > 1 && head h /= 0 = all (==0) (map head t) && triSup (map (drop 1) t)
             | head h == 0 = False
             | otherwise = True

-- (h)

rotateLeft :: Mat a -> Mat a
rotateLeft [] = []
rotateLeft m | length (head m) > 0 = map last m : rotateLeft (map init m)
             | otherwise = []