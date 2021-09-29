

--1

data Frac = F Integer Integer




-- (a)

normaliza :: Frac -> Frac
normaliza (F x y) | y<0 = F (-(div x m)) (-(div y m))
                  | otherwise = F (div x m) (div y m)
         where m = mdc (abs x) (abs y)

mdc :: Integer -> Integer -> Integer
mdc x y | x<y = mdc (y-x) x
        | x>y = mdc (x-y) y
        | x==0 = y

-- (b)

instance Eq Frac where
   f1 == f2 = n1==n2 && d1==d2
       where F n1 d1 = normaliza f1
             F n2 d2 = normaliza f2

-- (c)
{-
instance Ord Frac where
      (F x1 y1) < (F x2 y2) | x1 == x2 && y1 > y2 = True
                            | y1 == y2 && x1 < x2 = True
                          -}

-- (d)

instance Show Frac where
    show (F x y) = show x ++ "/" ++ show y 


-- (2)

data Exp a = Const a
           | Simetrico (Exp a)
           | Mais (Exp a) (Exp a)
           | Menos (Exp a) (Exp a)
           | Mult (Exp a) (Exp a)


instance Show a => Show (Exp a) where
    show (Const a) = show a
    show (Simetrico a) = "(" ++ "-" ++ show a ++ ")"
    show (Mais a b) = "(" ++ show a ++ "+" ++ show b ++ ")"
    show (Menos a b) = "(" ++ show a ++ "-" ++ show b ++ ")"
    show (Mult a b) = show a ++ "*" ++ show b

instance Num a => Eq a => Eq (Exp a) where
	(Const a) == (Const b) = a == b
	(Simetrico a) == (Simetrico b) = a == b
	(Mais a b) == (Mais c d) = (a+b) == (c+d)
	(Menos a b) == (Menos c d) = (a-b) == (c-d)
	(Mult a b) == (Mult c d) = (a*b) == (c*d)

instance Num a => Num (Exp a) where
        (+) (Const a) (Const b) = a + b
        (-) (Const a) (Const b) = a - b
        (*) (Const a) (Const b) = a * b