



retiraIndiceLista :: Int -> [a] -> [a]
retiraIndiceLista i [] = []
retiraIndiceLista 0 (x:xs) = xs
retiraIndiceLista i (x:xs) = x:(retiraIndiceLista (i-1) xs)