--Exercicio 1 


perimetro :: Double -> Double

perimetro r = 2 * pi * r



dist :: (Double, Double) -> (Double, Double) -> Double

dist (x1 , y1) (x2 , y2) = sqrt((x1-x2)^2 + (y1-y2)^2)


primUlt :: [a] -> (a,a)

primUlt l = (head l, last l)


multiplo :: Int -> Int -> Bool

multiplo m n = (mod m n) == 0 


truncaImpar :: [a] -> [a]

truncaImpar l = if (mod (length l) 2 /= 0) then tail l else l


max2 :: Int -> Int -> Int

max2 x y = if (x>y) then x else y


max3 :: Int -> Int -> Int -> Int

max3 x y z = if ((max2 x y ) > z) then (max2 x y) else z


--Exercicio 2

nRaizes :: Double -> Double -> Double -> Int


nRaizes a b c = if ((b^2) - 4 * a * c) > 0 then 2  else ( if (((b^2) - 4 * a * c) == 0) then 1 else 0) 



raizes :: Double -> Double -> Double -> [Double]

raizes a b c = if ((nRaizes a b c) == 0) then [] else [(((-b) + sqrt((b^2) -4*a*c))/2*a) , (((-b) - sqrt((b^2) -4*a*c))/2*a)]

-- Exercicio 3

type Hora = (Int , Int)

valHora :: Hora -> Bool

valHora (h , m) = (h >= 0) && (h <=23) && (m >= 0) && (m <= 59)


compHora :: Hora -> Hora -> Bool

compHora (h1 , m1) (h2 , m2) = (h1 == h2) && (m1 > m2) || (h1>h2)


horaMin :: Hora -> Int

horaMin (h , m) = 60 * h + m


minHora :: Int -> Hora

minHora m | m >= 0 && m <= 1439 = (div m 60, mod m 60)


--horaDif' :: Hora -> Hora -> Int


horaDif (h1, m1) (h2, m2) = sqrt((h1-h2)^2) * 60 + sqrt((m1-m2)^2) 


adMin :: Int -> Hora -> Hora

adMin a (h , m) = minHora (horaMin (h , m) + a)


--Exercicio 4

data Hora2 = H Int Int deriving (Show,Eq)

valHora2 :: Hora2 -> Bool

valHora2 (H h m) = (h >= 0) && (h <=23) && (m >= 0) && (m <= 59)


compHora2 :: Hora2 -> Hora2 -> Bool

compHora2 (H h1 m1) (H h2 m2) = (h1 == h2) && (m1 > m2) || (h1>h2)


horaMin2 :: Hora2 -> Int

horaMin2 (H h m) = 60 * h + m


minHora2 :: Int -> Hora2

minHora2 m | m >= 0 && m <= 1439 = (H (div m 60) (mod m 60))


horaDif2 :: Hora2 -> Hora2 -> Int

horaDif2 (H h1 m1) (H h2 m2) = if (h1>=h2) then (if (m1>=m2) then ((h1-h2)*60 +(m1-m2)) else ((h1-h2)*60 +(m2-m1)))
                               else (if (m1>=m2) then ((h2-h1)*60 +(m1-m2)) else ((h2-h1)*60 +(m2-m1)))


adMin2 :: Int -> Hora2 -> Hora2

adMin2 a (H h m) = minHora2 (horaMin2 (H h m) + a)


--Exercicio 5

data Semaforo = Verde | Amarelo | Vermelho deriving (Show, Eq)


s1 :: Semaforo
s1 = Verde

s2 :: Semaforo
s2 = Amarelo

s3 :: Semaforo
s3 = Vermelho

next :: Semaforo -> Semaforo

next Verde = Amarelo
next Amarelo = Vermelho
next Vermelho = Verde

stop :: Semaforo -> Bool

stop Vermelho = True
stop _        = False


safe :: Semaforo -> Semaforo -> Bool

safe Vermelho _ = True
safe _ Vermelho = True
safe _ _        = False


--Exercicio 6

data Ponto = Cartesiano Double Double | Polar Double Double
             deriving (Show, Eq)


posx :: Ponto -> Double

posx (Cartesiano x y) = x
posx (Polar d a)      = d * sin(a)



posy :: Ponto -> Double

posy (Cartesiano x y) = y
posy (Polar d a)      = d * cos(a)



raio :: Ponto -> Double

raio (Cartesiano x y) = sqrt(x^2 + y^2)
raio (Polar d a)      = d


angulo :: Ponto -> Double

angulo (Cartesiano x y) = atan (y/x)
angulo (Polar d a)      = a

dist2 :: Ponto -> Ponto -> Double

dist2 (Cartesiano x1 y1) (Cartesiano x2 y2) = sqrt((x1-x2)^2 + (y1-y2)^2)
dist2 (Polar d1 a1) (Polar d2 a2)           = sqrt(((posx(Polar d1 a1)-posx(Polar d2 a2))^2) + ((posy(Polar d1 a1)-posy(Polar d2 a2))^2))


-- Exercicio 7

data Figura = Circulo Ponto Double | Retangulo Ponto Ponto | Triangulo Ponto Ponto Ponto deriving (Show , Eq)


poligono :: Figura -> Bool

poligono ( Circulo (Cartesiano x y) r) = False
poligono ( Retangulo (Cartesiano x1 y1) (Cartesiano x2 y2)) = True
poligono ( Triangulo (Cartesiano x1 y1) (Cartesiano x2 y2) (Cartesiano x3 y3)) = True


vertices :: Figura -> [Ponto]

vertices ( Circulo (Cartesiano x y) r) = []
vertices ( Retangulo (Cartesiano x1 y1) (Cartesiano x2 y2)) = [(Cartesiano x1 y1),(Cartesiano x2 y2),(Cartesiano x1 y2),(Cartesiano x2 y1)]
vertices ( Triangulo (Cartesiano x1 y1) (Cartesiano x2 y2) (Cartesiano x3 y3)) = [(Cartesiano x1 y1),(Cartesiano x2 y2),(Cartesiano x3 y3)]


area :: Figura -> Double

area (Triangulo (Cartesiano x1 y1) (Cartesiano x2 y2) (Cartesiano x3 y3)) =
       let a = dist2 (Cartesiano x1 y1) (Cartesiano x2 y2)
           b = dist2 (Cartesiano x2 y2) (Cartesiano x3 y3)
           c = dist2 (Cartesiano x3 y3) (Cartesiano x1 y1)
           s = (a+b+c) / 2 --semi-perimetro
       in sqrt (s*(s-a)*(s-b)*(s-c)) -- formula de Heron

area (Retangulo (Cartesiano x1 y1) (Cartesiano x2 y2)) =
       let b = dist2 (Cartesiano x1 y1) (Cartesiano x2 y1)
           h = dist2 (Cartesiano x1 y1) (Cartesiano x1 y2)
       in b*h

area (Circulo (Cartesiano x1 y2) r) = pi * r^2

-- Exercicio 8


isLower' :: Char -> Bool 

isLower' x = (x >= 'a') && (x <= 'z')


isDigit' :: Char -> Bool

isDigit' x = (x >= '1') && (x <= '9')


isAlpha' :: Char -> Bool

isAlpha' x = (x >= 'a') && (x <= 'z') || (x >= 'A') && (x <= 'Z')


toUpper' :: Char -> Char

toUpper' 'a' = 'A'
