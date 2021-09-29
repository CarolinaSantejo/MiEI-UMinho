-- Q U E R I E S

USE `filmedb` ;

-- Query 1
-- Filmes premiados organizados de forma decrescente (do mais premiado para o menos)
SELECT Título, Premios
FROM  (
	SELECT Nome AS Título, count(*) AS Premios
			,RANK() OVER (ORDER BY count(Nome) DESC)
	FROM Filme
    INNER JOIN FilmePrémioPessoa ON Filme.IDFilme = FilmePrémioPessoa.FilmeID
	WHERE FilmePrémioPessoa.Vencedor = 'Sim'
	GROUP  BY Nome
    ) sub;

-- Query 3 
-- Todos os atores que são diretores
SELECT DISTINCT Nome
FROM Função
INNER JOIN FunçãoPessoaFilme ON Função.IDFunção = FunçãoPessoaFilme.IDFunção
INNER JOIN Pessoa ON FunçãoPessoaFilme.IDPessoa = Pessoa.IdPessoa
INNER JOIN FilmePessoa ON FunçãoPessoaFilme.IdPessoa = FilmePessoa.IDPessoa;


-- Query 4 
-- Todos os filmes de um dado ator que estrearam depois de um certo ano
-- Consideremos o ano 2005 e o ator Leonardo Dicaprio
SELECT TítuloFilme AS Título
FROM Filme
INNER JOIN FilmePessoa ON Filme.IDFilme = FilmePessoa.IDFilme
INNER JOIN Pessoa ON FilmePessoa.IdPessoa = Pessoa.IdPessoa
WHERE FilmePessoa.IDPessoa='11' AND YEAR(DataEstreia)>2005;


-- Query 6
-- Quantas mulheres estiveram presentes num dado filme
-- Consideremos o filme Titanic

SELECT DISTINCT Nome
FROM Filme
INNER JOIN FilmePessoa ON Filme.IdFilme = FilmePessoa.IdFilme
INNER JOIN Pessoa ON FilmePessoa.IdPessoa = Pessoa.IDPessoa
WHERE Filme.IdFilme = '1' AND Pessoa.Género = 'F' 
UNION
SELECT DISTINCT Nome
FROM Filme
INNER JOIN FunçãoPessoaFilme ON Filme.IdFilme = FunçãoPessoaFilme.IdFilme
INNER JOIN Pessoa ON FunçãoPessoaFilme.IdPessoa = Pessoa.IDPessoa
WHERE Filme.IdFilme = '1' AND Pessoa.Género = 'F' ;




