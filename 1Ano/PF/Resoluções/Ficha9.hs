
import System.Random
import Data.List


--1

--(b)

main :: IO ()
main = masterMind 15



masterMind :: Int -> IO ()
masterMind n = do x <- geraCodigo
                  daRespostas x n


geraCodigo1 :: IO [Int]
geraCodigo1 = do x <- randomRIO (1,9)
                 y <- randomRIO (1,9)
                 z <- randomRIO (1,9)
                 w <- randomRIO (1,9)
                 return [x,y,z,w]


geraCodigo :: IO [Char]
geraCodigo = sequence (replicate 4 (randomRIO ('1','9')))


daRespostas :: [Char] -> Int -> IO ()
daRespostas s 0 = putStrLn ("Ja Fostes. O segredo era:" ++ show s)
daRespostas segredo n = do putStrLn "Jogada?"
                           resp <- getLine
                           let (cc,ce) = calcula segredo resp
                           putStrLn ((show cc) ++ " numeros certos na posição certa")
                           putStrLn ((show ce) ++ " numeros certos na posição errada")
                           if (cc==4) then putStrLn "Parabens\007\007\007"
                           else do putStrLn "Tente novamente"
                                   daRespostas segredo (n-1)



calcula :: String -> String -> (Int,Int)
calcula seg resp = (certos , comuns-certos)
            where comuns = length (intersect seg resp)
                  certos = cc seg resp
                  cc seg resp = length (filter id (zipWith (==) seg resp))
                 --ou
{--                 cc [] [] = 0
                    cc (x:xs) (y:ys) | x==y = 1 + cc xs ys
                                     | otherwise = cc xs ys --}


-- (a)

bingo :: IO [Int]
bingo = bingoX (enumFromTo 1 99)


bingoX [] = return []
bingoX l = do p <- randomRIO (0, (length l)-1)
              let x = l !! p
              let y = (take p l) ++ (tail (drop p l))
              xs <- bingoX y
              return (x:xs)

bingoS :: [Int] -> IO [Int]
bingoS l = if (length l == 99) then return l
           else do x <- randomRIO (1,99)
                   if (elem x l) then bingoS l
                                 else bingoS (x:l)

