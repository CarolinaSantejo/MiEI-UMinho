

--1

enumFromto :: Int -> Int -> [Int]
enumFromto min max | min == max = [max]
                   | otherwise = [min] ++ enumFromto (min + 1) max

--2

enumFromthenTo :: Int -> Int -> Int -> [Int]
enumFromthenTo a x b | a == b = [b]
                     | otherwise = [a] ++ enumFromthenTo (a+(x-a)) (x+(x-a)) b

--3

(+++) :: [Int] -> [Int] -> [Int]
(+++) l [] = l
(+++) [] l = l
(+++) [a] l = a:l
(+++) (h1:t1) l = h1 : (+++) t1 l 

--4

(!!!) :: [a] -> Int -> a
(!!!) (h:t) 0 = h
(!!!) (h:t) i = (!!!) t (i-1)

--5

reverse' :: [a] -> [a]
reverse' [] = []
reverse' [a] = [a]
reverse' (h:t) = auxRev h (reverse' t)


auxRev :: a -> [a] -> [a]
auxRev h l = l ++ [h]

--6

take' :: Int -> [a] -> [a]
take' 0 l = []
take' 1 (h:t) = [h]
take' x (h:t) | x > length (h:t) = (h:t)
              | otherwise = h : take' (x-1) t

-- 7

drop' :: Int -> [a] -> [a]
drop' 0 l = l
drop' 1 (h:t) = t
drop' x (h:t) | x > length (h:t) = []
              | otherwise = drop' (x-1) t


--8

zip' :: [a] -> [b] -> [(a,b)]
zip' l [] = []
zip' [] l = []
zip' (h1:t1) (h2:t2) = (h1,h2) : zip' t1 t2 

--9

elem' :: Eq a => a -> [a] -> Bool
elem' a [] = False
elem' a (h:t) | a == h = True
              | otherwise = elem' a t

--10

replicate' :: Int -> a -> [a]
replicate' 0 a = []
replicate' x a = a : replicate' (x-1) a

--11

intersperce' :: a -> [a] -> [a]
intersperce' a [] = []
intersperce' a [b] = [b]
intersperce' a (h:[t]) = [h,a,t]
intersperce' a (h1:h2:t) = h1 : a : intersperce' a (h2:t)

--12

group' :: Eq a => [a] -> [[a]]
group' [] = []
group' [a] = [[a]]
group' (h:t) = auxGroup h t : group' (remGroup h t)


auxGroup :: Eq a => a -> [a] -> [a]
auxGroup a [] = [a]
auxGroup a (h:t) | a == h = h : auxGroup a t
                 | otherwise = [a]

remGroup :: Eq a => a -> [a] -> [a]
remGroup a [] = []
remGroup a (h:t) | a == h = remGroup a t
                 | otherwise = h:t

--13

concatena :: [[a]] -> [a]
concatena [] = []
concatena [h] = h
concatena (h1:h2:t) = h1 ++ h2 ++ concatena t

--14

inits' :: [a] -> [[a]]
inits' [] = []
inits' l = [] : initsAux 1 l

initsAux :: Int -> [a] -> [[a]]
initsAux x l | x <= length l = take x l : initsAux (x+1) l
             | otherwise = []


--15 

tails' :: [a] -> [[a]]
tails' [] = []
tails' l = tailsAux 0 l

tailsAux :: Int -> [a] -> [[a]]
tailsAux x l | x <= length l = drop x l : tailsAux (x+1) l
             | otherwise = []

--16

isPrefixOf' :: Eq a => [a] -> [a] -> Bool
isPrefixOf' [] _ = True
isPrefixOf' _ [] = False
isPrefixOf' (h1:t1) (h2:t2) | h1 == h2 = isPrefixOf' t1 t2
                            | otherwise = False

--17 

isSuffixOf :: Eq a => [a] -> [a] -> Bool
isSuffixOf [] _ = True
isSuffixOf _ [] = False
isSuffixOf l1 l2 = isPrefixOf' (reverse l1) (reverse l2)                            


--18

isSubsequenceOf :: Eq a => [a] -> [a] -> Bool
isSubsequenceOf [] _ = True
isSubsequenceOf _ [] = False
isSubsequenceOf (h1:t1) (h2:t2) | h1 == h2 = isSubsequenceOf t1 t2
                                | otherwise = isSubsequenceOf (h1:t1) t2

--19

elemIndices :: Eq a => a -> [a] -> [Int]
elemIndices a [] = []
elemIndices a (h:t) = elemAux 0 a (h:t)


elemAux :: Eq a => Int -> a -> [a] -> [Int]
elemAux i a [] = []
elemAux i a (h:t) | a == h = i : elemAux (i+1) a t
                  | otherwise = elemAux (i+1) a t


--20

nub :: Eq a => [a] -> [a]
nub [] = []
nub (h:t) = h : nub (nubAux h t)

nubAux :: Eq a => a -> [a] -> [a]
nubAux a [] = []
nubAux a (h:t) | a == h = nubAux a t
               | otherwise = h : nubAux a t


--21

delete :: Eq a => a -> [a] -> [a]
delete a [] = []
delete a (h:t) | a == h = t
               | otherwise = h : delete a t


--22

(\\) :: Eq a => [a] -> [a] -> [a]
(\\) l [] = l
(\\) [] l = []
(\\) l (h:t) = delete h ((\\) l t)

--23

union :: Eq a => [a] -> [a] -> [a]
union l [] = l
union [] l = []
union l (h:t) | elem h l = union l t
              | otherwise = union (l ++ [h]) t

--24

intersect :: Eq a => [a] -> [a] -> [a]
intersect l [] = l
intersect [] l = []
intersect (h:t) l | elem h l = h : intersect t l
                  | otherwise = intersect t l


--25

insert :: Ord a => a -> [a] -> [a]
insert x [] = [x]
insert x (h:t) | x<=h = x : h : t
               | otherwise = h : insert x t


--26

unwords' :: [String] -> String
unwords' [] = ""
unwords' [h] = h
unwords' (h:t) = h ++ " " ++ unwords' t

--27


unlines' :: [String] -> String
unlines' [] = ""
unlines' (h:t) = h ++ "\n" ++ unlines' t

--28

pMaior :: Ord a => [a] -> Int
pMaior (h1:h2:t) = maiorAux 1 0 h1 (h2:t)


maiorAux :: Ord a => Int -> Int -> a -> [a] -> Int
maiorAux i m a [] = m
maiorAux i m a (h:t) | h>a = maiorAux (i+1) i h t
                     | otherwise = maiorAux (i+1) m a t

--29

temRepetidos :: Eq a => [a] -> Bool
temRepetidos [] = False
temRepetidos (h:t) | elem h t = True
                   | otherwise = temRepetidos t

--30

algarismos :: [Char] -> [Char]
algarismos [] = []
algarismos (h:t) | h >= '0' && h <= '9' = h : algarismos t
                 | otherwise = algarismos t

--31

posImpares :: [a] -> [a]
posImpares [] = []
posImpares [a] = []
posImpares (h1:h2:t) = h2 : posImpares t

--32

posPares :: [a] -> [a]
posPares [] = []
posPares [a] = [a]
posPares (h1:h2:t) = h1 : posPares t

--33

isSorted :: Ord a => [a] -> Bool
isSorted [] = True
isSorted [a] = True
isSorted (h1:h2:t) | h1 <= h2 = isSorted (h2:t)
                   | otherwise = False

--34

iSort :: Ord a => [a] -> [a]
iSort [] = []
iSort (h:t) = insert h (iSort t)

--35

menor :: String -> String -> Bool
menor [] _ = True
menor _ [] = False
menor (h1:t1) (h2:t2) | h1<h2 = True
                      | h1==h2 = menor t1 t2
                      | otherwise = False


--36

elemMSet :: Eq a => a -> [(a,Int)] -> Bool
elemMSet a [] = False
elemMSet a ((b,x):t) | a == b = True
                     | otherwise = elemMSet a t

--37

lengthMSet :: [(a,Int)] -> Int
lengthMSet [] = 0
lengthMSet (h:t) = snd h + lengthMSet t


--38

converteMSet :: [(a,Int)] -> [a]
converteMSet [] = []
converteMSet (h:t) = replicate (snd h) (fst h) ++ converteMSet t

--39

insereMSet :: Eq a => a -> [(a,Int)] -> [(a,Int)]
insereMSet a [] = [(a,1)]
insereMSet a ((b,x):t) | a == b = (b,x+1) :t
                       | otherwise = (b,x) : insereMSet a t

--40

removeMSet :: Eq a => a -> [(a,Int)] -> [(a,Int)]
removeMSet a [] = []
removeMSet a ((b,x):t) | a == b && x == 1 = t
                       | a == b = (b,x-1) : t
                       | otherwise = (b,x) : removeMSet a t


--41

constroiMSet :: Ord a => [a] -> [(a,Int)]
constroiMSet [] = []
constroiMSet l = constroiAux 1 l


constroiAux :: Ord a => Int -> [a] -> [(a,Int)]
constroiAux i [a] = [(a,i)]
constroiAux i (h1:h2:t) | h1 == h2 = constroiAux (i+1) (h2:t)
                        | otherwise = (h1,i) : constroiAux 1 (h2:t)


--42

partitionEithers :: [Either a b] -> ([a],[b])
partitionEithers l = (left l,right l)

left :: [Either a b] -> [a]
left [] = []
left (Left a : t) = a : left t
left (Right a : t) = left t

right :: [Either a b] -> [b]
right [] = []
right (Left a : t) = right t
right (Right a : t) = a :right t


--43

catMaybes :: [Maybe a] -> [a]
catMaybes [] = []
catMaybes (Just a : t) = a : catMaybes t
catMaybes (Nothing : t) = catMaybes t


data Movimento = Norte | Sul | Este | Oeste
               deriving Show

--44

posicao :: (Int,Int) -> [Movimento] -> (Int,Int)
posicao (x,y) [] = (x,y)
posicao (x,y) (Norte : t) = posicao (x,y+1) t
posicao (x,y) (Sul : t) = posicao (x,y-1) t
posicao (x,y) (Este : t) = posicao (x+1,y) t
posicao (x,y) (Oeste : t) = posicao (x-1,y) t

--45

caminho :: (Int,Int) -> (Int,Int) -> [Movimento]
caminho (x1,y1) (x2,y2) | x1 < x2 && y1 < y2 = replicate (x2-x1) Este ++ replicate (y2-y1) Norte
                        | x1 < x2 && y1 > y2 = replicate (x2-x1) Este ++ replicate (y1-y2) Sul
                        | x1 > x2 && y1 < y2 = replicate (x1-x2) Oeste ++ replicate (y2-y1) Norte
                        | x1 > x2 && y1 > y2 = replicate (x1-x2) Oeste ++ replicate (y1-y2) Sul
                        | x1 == x2 && y1 > y2 = replicate (y1-y2) Sul
                        | x1 == x2 && y1 < y2 = replicate (y2-y1) Norte
                        | x1 < x2 && y1 == y2 = replicate (x2-x1) Este
                        | x1 > x2 && y1 == y2 = replicate (x1-x2) Oeste


--46

vertical :: [Movimento] -> Bool
vertical [] = True
vertical (Norte:t) = vertical t
vertical (Sul:t) = vertical t
vertical (Este:t) = False
vertical (Oeste:t) = False


data Posicao = Pos Int Int
             deriving Show


--47

maisCentral :: [Posicao] -> Posicao
maisCentral [Pos x y] = Pos x y
maisCentral (Pos x y :t) = centralAux (Pos x y) (maisCentral t)


centralAux :: Posicao -> Posicao -> Posicao
centralAux (Pos x1 y1) (Pos x2 y2) | x1^2+y1^2<x2^2+y2^2 = Pos x1 y1
                                   | otherwise = Pos x2 y2


--48

vizinhos :: Posicao -> [Posicao] -> [Posicao]
vizinhos (Pos x y) [] = []
vizinhos (Pos x1 y1) ((Pos x2 y2) :t) | x2 == x1 && (y2==y1+1 || y2==y1-1) = (Pos x2 y2) : vizinhos (Pos x1 y1) t
                                      | y2 == y1 && (x2==x1+1 || x2==x1-1) = (Pos x2 y2) : vizinhos (Pos x1 y1) t
                                      | otherwise = vizinhos (Pos x1 y1) t


--49

mesmaOrdenada :: [Posicao] -> Bool
mesmaOrdenada [] = True
mesmaOrdenada [Pos x y] = True
mesmaOrdenada (Pos x1 y1: Pos x2 y2 :t) | y1==y2 = mesmaOrdenada (Pos x2 y2 :t)
                                        | otherwise = False


data Semaforo = Verde | Amarelo | Vermelho
              deriving Show


--50

intersecaoOk :: [Semaforo] -> Bool
intersecaoOk [s] = True
intersecaoOk (h:t) = auxOK h t


auxOK :: Semaforo -> [Semaforo] -> Bool
auxOK (Vermelho) [] = True
auxOK (Verde) [] = True
auxOK (Amarelo) [] = True
auxOK (Vermelho) (Verde : t) = auxOK (Verde) t
auxOK (Vermelho) (Amarelo : t) = auxOK (Amarelo) t
auxOK Verde (Verde : t) = False
auxOK Amarelo (Verde : t) = False
auxOK Verde (Amarelo : t) = False
auxOK Amarelo (Amarelo : t) = False
auxOK Verde (Vermelho : t) = auxOK (Verde) t
auxOK Amarelo (Vermelho : t) = auxOK (Verde) t