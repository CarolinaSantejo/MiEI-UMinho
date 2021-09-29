import Data.Char
import Data.List
import Cp


-- Ficha 1

h :: Char -> Int
h x = digitToInt x

k :: Double -> Float
k x = 1.56

f :: Int -> Bool
f x = True

g :: Float -> Float
g x = 1.8945

collect :: (Ord b, Ord a) => [(a,b)] -> [(a,[b])]
collect [] = []   

discollect :: (Ord b, Ord a) =>  [(a,[b])] -> [(a,b)]
discollect[] = []


converse :: [(a,b)] -> [(b,a)]
converse [] = []


comp :: [(a,b)] -> [(b,c)] -> [(a,c)]
comp [] [] = []

-- Ficha 2

data LTree a = Leaf a | Fork (LTree a, LTree a) deriving Show

outLT(Leaf a) = i1 a
outLT(Fork(x,y)) = i2 (x,y)

inLT :: Either a (LTree a, LTree a) -> LTree a 
inLT (Left a) = Leaf a
inLT (Right (x,y)) = Fork (x,y)

fLT k = id-|-(k><k)

cataLT g = g . fLT(cataLT g) . outLT

-- Ficha 3

-- alpha = (id + p1) . i2 . p2

-- Ficha 5

fL k = id -|- id >< k

fN k = id -|- k

outN 0 = i1()
outN n = i2 (n-1)

outL [] = i1()
outL (a:x) = i2(a,x)

cataN g = g . fN(cataN g) . outN

cataL g = g . fL(cataL g) . outL

for b i = cataN(either (const i) b)

t = p2 . aux where aux = for (split(succ.p1) mul) (1,1) 

-- Ficha 6

-- Multiplica todos elementos da lista 

multL = cataL (either (const 1) mul)

myReverse = cataL (either nil g) where g (a,b) = b++[a]

myConcat = cataL (either nil g) where g (a,b) = a++b

myMap f = cataL (either nil (cons.(f><id)))

myMax = cataL (either (const 0) (uncurry max))

myFilter p = cataL (either nil (h p)) where h p (h,t) = x ++ t where x = if (p h) then [h] else []

data Exp v o =   Var v              -- expressions are either variables
               | Term o [ Exp v o ] -- or terms involving operators and
                                    -- subterms
               deriving (Show,Eq,Ord)

inExp = either Var (uncurry Term)
outExp(Var v) = i1 v
outExp(Term o l) = i2(o,l)

baseExp f g h = f -|- (g >< map h)

recExp x = baseExp id id x

cataExp g = g . recExp (cataExp g) . outExp

vars = cataExp(either singl (concat . p2))

vars1 (Var v) = singl v
vars1 (Term o l) = concat(map vars l)