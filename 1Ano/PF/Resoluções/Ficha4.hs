

--3

import Data.Char

digitAlpha :: String -> (String,String)
digitAlpha [] = ([],[])
digitAlpha (h:t) | isDigit h = let (x,y) = digitAlpha t 
                               in (x,h:y)
                 | isAlpha h = let (x,y) = digitAlpha t 
                               in (h:x,y)


--4

--nzp :: [Int] -> (Int,Int,Int)
--nzp [] = (0,0,0)
--nzp (h:t) = let (n,z,p) = nzp t
    --        in (if h==0 then (n,z+1,p)
  --          	else if h<0 then (n+1,z,p)
--            		        else (n,z,p+1)


--6

fromDigits :: [Int] -> Int
fromDigits [] = 0
fromDigits l = auxDig (reverse l)


auxDig [] = 0
auxDig (h:t) = h + 10 * auxDig t
