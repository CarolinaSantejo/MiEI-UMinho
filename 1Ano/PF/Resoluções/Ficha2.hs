
import Data.Char
import Cp
--Exercicio 2

dobros :: [Float] -> [Float]

dobros []    = []
dobros (h:t) = (2*h) : (dobros t)



numOcorre :: Char -> String -> Int

numOcorre c [] = 0
numOcorre c (h : t) = if (c==h) then (1 + (numOcorre c t)) else (numOcorre c t)



positivos :: [Int] -> Bool

positivos [x] = x>0
positivos (h : t) = h>0 && positivos t


soPos :: [Int] -> [Int]

soPos [] = [] 
soPos (h : t) = if (h < 0) then (soPos t) else h:(soPos t)


somaNeg :: [Int] -> Int

somaNeg [] = 0
somaNeg (h : t) = if (h < 0) then h + (somaNeg t) else (somaNeg t)

--Exercicio 3


soDigitos :: [Char] -> [Char]
soDigitos [] = []
soDigitos (h:t) | isDigit h = h : soDigitos t
                | otherwise = soDigitos t

minusculas :: [Char] -> Int
minusculas [] = 0
minusculas (h:t) | h >= 'a' && h <='z' = 1 + minusculas t
                 | otherwise = minusculas t

nums :: String -> [Int]
nums "" = []
nums (h:t) | isDigit h = digitToInt h : nums t
           | otherwise = nums t
