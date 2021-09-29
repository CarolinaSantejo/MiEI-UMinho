-- Q U E R I E S

USE `filmedb` ;

-- Query 1
-- Filmes premiados organizados de forma decrescente
SELECT Título, Premios
FROM  (
	SELECT Nome AS Título, count(*) AS Premios
			,rank() OVER (ORDER BY count(Nome) DESC)
	FROM Filme
    INNER JOIN FilmePrémioPessoa ON Filme.IDFilme = FilmePrémioPessoa.FilmeID
	WHERE FilmePrémioPessoa.Vencedor = 'Sim'
	GROUP  BY Nome
    ) sub;
    
-------------------    
-- Query 2

SELECT  Filme.Nome, AVG(Rating) AS RatingMedia
FROM filme
INNER JOIN Review ON Review.IDFilme = Filme.IdFilme
WHERE Filme.Nome = 'Aquaman';    


------------------


-- Query 3 
-- Todos os atores que executam funções backstage num dado filme
SELECT DISTINCT Nome
FROM Pessoa
INNER JOIN FunçãoPessoaFilme ON Pessoa.IDPessoa = FunçãoPessoaFilme.IDPessoa
INNER JOIN FilmePessoa ON (FunçãoPessoaFilme.IdPessoa = FilmePessoa.IDPessoa AND FunçãoPessoaFilme.IdFilme = FilmePessoa.IDFilme);


----------------

-- Query 4 
-- Todos os filmes de um dado ator que estrearam depois de um certo ano
-- Consideremos o ano 2005 e o ator Leonardo Dicaprio
SELECT Filme.Nome AS TítuloFilme
FROM Filme
INNER JOIN FilmePessoa ON Filme.IDFilme = FilmePessoa.IDFilme
INNER JOIN Pessoa ON FilmePessoa.IdPessoa = Pessoa.IdPessoa
WHERE Pessoa.Nome='Leonardo Dicaprio' AND YEAR(DataEstreia)>2005;

-------------------

-- Query 5

-- Cast e Crew de um Filme

SELECT DISTINCT Pessoa.Nome
FROM FilmePessoa
INNER JOIN Pessoa ON FilmePessoa.IdPessoa = Pessoa.IDPessoa
INNER JOIN Filme ON FilmePessoa.IDFilme = Filme.IDFilme
WHERE Filme.Nome = 'Aquaman'
UNION
SELECT DISTINCT Pessoa.Nome
FROM FunçãoPessoaFilme
INNER JOIN Pessoa ON FunçãoPessoaFilme.IdPessoa = Pessoa.IDPessoa
INNER JOIN Filme ON FunçãoPessoaFilme.IDFilme = Filme.IDFilme
WHERE Filme.Nome = 'Aquaman';

-------------------------

-- Query 6
-- Quantas mulheres estiveram presentes num dado filme
-- Consideremos o filme Titanic

SELECT DISTINCT Pessoa.Nome
FROM Filme
INNER JOIN FilmePessoa ON Filme.IdFilme = FilmePessoa.IdFilme
INNER JOIN Pessoa ON FilmePessoa.IdPessoa = Pessoa.IDPessoa
WHERE Filme.Nome = 'Titanic' AND Pessoa.Género = 'F' 
UNION
SELECT DISTINCT Pessoa.Nome
FROM Filme
INNER JOIN FunçãoPessoaFilme ON Filme.IdFilme = FunçãoPessoaFilme.IdFilme
INNER JOIN Pessoa ON FunçãoPessoaFilme.IdPessoa = Pessoa.IDPessoa
WHERE Filme.Nome = 'Titanic' AND Pessoa.Género = 'F' ;

---------------------------
-- Query 7 (opcional)
-- Nomeacoes nao vencidas de um filme

SELECT DISTINCT Filme.Nome, COUNT(*) AS NomeacoesNaoVencidas
FROM Filme
INNER JOIN FilmePrémioPessoa ON Filme.IdFilme = FilmePrémioPessoa.FilmeID
WHERE Filme.Nome = 'Titanic' AND FilmePrémioPessoa.Vencedor = 'Não';




