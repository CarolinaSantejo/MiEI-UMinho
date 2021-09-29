-- V I S T A S

USE `filmeDB` ;

CREATE VIEW view_Filme AS
SELECT TítuloFilme ,Língua,Duração,PG,Descrição
FROM Filme;

---------------

CREATE VIEW view_User AS
SELECT UserName,email,nível
FROM User;

------------------

CREATE VIEW view_PrémioCategoria AS
SELECT distinct Categoria
FROM Prémio;

-----------------

CREATE VIEW view_Reviews AS
SELECT TítuloFilme,UserName,dataReview,Rating,Comentario
FROM Filme INNER JOIN Review ON idFilme = IdFilmeReview;


