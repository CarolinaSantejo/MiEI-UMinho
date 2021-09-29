-- Í N D I C E S 

USE `filmeDB` ;

-- SHOW INDEX FROM Filme;
CREATE INDEX idx_Língua 
	ON Filme (Língua);
    
    -------------
    -- SHOW INDEX FROM Filme;
CREATE INDEX idx_PG 
	ON Filme (PG);
    
    -------------
    
  -- SHOW INDEX FROM FilmePrémioPessoa;    
CREATE INDEX idx_AnoPrémio 
	ON FilmePrémioPessoa (Ano);  
    
    -------------
    
   -- SHOW INDEX FROM Prémio;    
CREATE INDEX idx_Categoria 
	ON Prémio (Categoria);   
    
  ---------------
  
    -- SHOW INDEX FROM User;    
CREATE INDEX idx_Nivel 
	ON User (Nível); 
    
     
    
 
 