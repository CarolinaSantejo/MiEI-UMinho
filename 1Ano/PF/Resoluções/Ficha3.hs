
--1

data Hora = H Int Int
           deriving Show

h1 :: Hora
h1 = H 9 0

type Etapa = (Hora , Hora)

type Viagem = [Etapa]

horaValida :: Hora -> Bool
horaValida (H h m) = (h>=0 && h<24) || (m>=0 && m<60)
 
testeEtapa :: Etapa -> Bool
testeEtapa (H h1 m1 , H h2 m2) = (h1<h2) || (h1==h2 && m1<m2) && horaValida (H h1 m1) && horaValida (H h2 m2)

testeViagem :: Viagem -> Bool
testeViagem [e] = testeEtapa e
testeViagem (e1:e2:es) = testeEtapa e1 && testeEtapa (snd e1 , fst e2) && testeViagem (e2:es)

partidaChegada :: Viagem -> (Hora,Hora)
partidaChegada v = (fst(head v) , snd(last v))

horaMin :: Hora -> Int
horaMin (H h m) = 60 * h + m

minHora :: Int -> Hora
minHora m | m >= 0 && m <= 1439 = (H (div m 60) (mod m 60))

horaDif :: Hora -> Hora -> Int
horaDif (H h1 m1) (H h2 m2) = if (h1>=h2) then (if (m1>=m2) then ((h1-h2)*60 +(m1-m2)) else ((h1-h2)*60 +(m2-m1)))
                               else (if (m1>=m2) then ((h2-h1)*60 +(m1-m2)) else ((h2-h1)*60 +(m2-m1)))

{--
tempoViagem :: Viagem -> Int
tempoViagem [] = 0
tempoViagem (e:es) = (horaDif e) + (tempoViagem es)

tempoTotalEspera :: Viagem -> Int
tempoTotalEspera v = let aViajar = tempoViagem v
                         (h1,h2) = partidaChegada v
                         minutosTotais = horaMin h2 - horaMin h1
                      in minutosTotais - aViajar
--}


--2

data Ponto = Cartesiano Double Double | Polar Double Double
             deriving (Show, Eq)
type Poligonal = [Ponto]

posx :: Ponto -> Double
posx (Cartesiano x y) = x
posx (Polar d a)      = d * sin(a)


posy :: Ponto -> Double
posy (Cartesiano x y) = y
posy (Polar d a)      = d * cos(a)


dist :: Ponto -> Ponto -> Double
dist (Cartesiano x1 y1) (Cartesiano x2 y2) = sqrt((x1-x2)^2 + (y1-y2)^2)
dist (Polar d1 a1) (Polar d2 a2)           = sqrt(((posx(Polar d1 a1)-posx(Polar d2 a2))^2) + ((posy(Polar d1 a1)-posy(Polar d2 a2))^2))

comprimento :: Poligonal -> Double
comprimento (p1:p2:[]) = dist p1 p2
comprimento (p1:p2:pts) = dist p1 p2 + comprimento (p2:pts)

eFechado :: Poligonal -> Bool
eFechado p = head p == last p

data Figura = Circulo Ponto Double | Retangulo Ponto Ponto | Triangulo Ponto Ponto Ponto deriving (Show , Eq)

triangula :: Poligonal -> [Figura] 
triangula [p] = []
triangula (p1:p2:p3:pts) = (Triangulo p1 p2 p3) : triangula(p2:p3:pts)

difPontos :: Ponto -> Ponto -> Ponto
difPontos (Cartesiano x1 y1) (Cartesiano x2 y2) = Cartesiano (x1-x2) (y1-y2)

somaPontos :: Ponto -> Ponto -> Ponto
somaPontos (Cartesiano x1 y1) (Cartesiano x2 y2) = Cartesiano (x1+x2) (y1+y2)



mover :: Poligonal -> Ponto -> Poligonal
mover (Cartesiano x1 y1 : t) (Cartesiano x2 y2) = (Cartesiano x2 y2) : auxMover (difPontos (Cartesiano x2 y2) (Cartesiano x1 y1)) t
                   where auxMover p [] = []
                         auxMover p (Cartesiano x y : t) = somaPontos p (Cartesiano x y) : auxMover p t



zoom :: Double -> Poligonal -> Poligonal
zoom n (Cartesiano x y : t) = Cartesiano x y : auxzoom n t
                where auxzoom n [] = []
                      auxzoom n (Cartesiano x y : t) = Cartesiano (n*x) (n*y) : auxzoom n t


--3

data Contacto = Casa Integer
              | Trab Integer
              | Tlm Integer
              | Email String
              deriving Show

type Nome = String
type Agenda = [(Nome,[Contacto])]


acrescEmail :: Nome -> String -> Agenda -> Agenda
acrescEmail n e ((n1,(c:t1)) : t) = (n, [Email e]) : (n1,(c:t1)) : t 

verEmails :: Nome -> Agenda -> Maybe [String]
verEmails n [] = Nothing
verEmails n ((n1,(Email x : t1)) : t) | n == n1 = Just (x: auxE n t1)
                                      | otherwise = verEmails n t 
verEmails n ((n1,(_:t1)) :t) | n==n1 = verEmails n ((n1,t1) :t)
                             | otherwise = verEmails n t
                    
auxE :: Nome -> [Contacto] -> [String]
auxE n [] = []
auxE n (Email x : t) =  x : auxE n t
auxE n (_ :t) = auxE n t


