


reverse' :: [a] -> [a]
reverse' [] = []
reverse' (h:t) = aux h (reverse' t)


aux :: a -> [a] -> [a]
aux x [] = [x]
aux x (h:t) = h : aux x t