-- Implementação e Exploração de Sistemas de Bases de Dados Relacionais
-- O caso de Estudo da "Movie Database"
-- Povoamento da Base de Dados Relacional        

USE `filmeDB` ;
-- DELETE FROM `filmeDB`.`Filme`;
-- SELECT * FROM `filmeDB`.`Filme`;
INSERT INTO `filmeDB`.`Filme`
-- Custo é em doláres
(IdFilme,Nome,Custo,País,Língua,Duração,Receita,PG,Descrição,DataEstreia)
VALUES
	('1','Titanic','200000000','EUA','Inglês','3:14','1200000000','12','A young aristocrat falls in love with a kind but poor artist','1997-12-19'),
    ('2','The Shawshank Redemption','25000000','EUA','Inglês','2:22','58300000','16','Two imprisoned men bond over a number of years, finding redemption','1994-11-23'),
	('3','Aquaman','160000000','EUA','Inglês','2:23','1148000000','12','Arthur Curry goes on a quest to prevent a war between  ocean and land.','2018-11-21'),
	('4','Parasite','11400000','Coreia do Sul','Coreano','2:12','264400000','14','Greed and class discrimination threaten the relationship between  Park family and the Kim clan.','2019-05-21'),
    ('5','Gladiator','103000000','EUA','Inglês','2:35','46050000','12','A former Roman General sets out to exact vengeance against the corrupt emperor','2000-05-19'),
    ('6','By The Sea','10000000','EUA','Inglês','2:02','3334927','18','A couple tries to repair their marriage while staying at a hotel in France.','2015-12-09'),
	('7','Inception','160000000','EUA','Inglês','2:28','836800000','12','A thief who steals corporate secrets through the use of dream-sharing technology','2010-06-22'),
    ('8','Inside Out','175000000','EUA','Inglês','1:35','857611174','6','Young Riley conflicts on how to navigate on new city.','2015-06-18'),
    ('9','La Vie en Rose','25000000','França','Francês','2:20','86300000','12','Biopic of the iconic French singer Édith Piaf.','2009-04-25');
	
    
  -------//---------
    
-- DELETE FROM `mydb`.`Género`;
-- SELECT * FROM `mydb`.`Género`;
INSERT INTO `filmeDB`.`Género`
(IDGenero,Nome)
VALUES
	('1','Ação'),
    ('2','Aventura'),
	('3','Romance'),
	('4','Terror'),
    ('5', 'Comédia'),
    ('6','Suspense'),
    ('7', 'Animação'),
    ('8', 'Familiar'),
    ('9', 'Crime'),
    ('10', 'Drama'),
    ('11','Fantasia'),
    ('12', 'Sci-Fi'),
    ('13','Mistério'),
    ('14', 'Biografia'),
    ('15', 'Thriller'),
    ('16', 'Musical');
    -----//------  
    

-- DELETE FROM `filmeDB`.`FilmeGenero`;
-- SELECT * FROM `filmeDB`.`filmeGenero`;

INSERT INTO `filmeDB`.`FilmeGenero`
(FilmeID,GeneroID)   
VALUES
	('1','10'),
    ('1','3'),
    ('2','10'),
	('2','9'),
	('3','1'),
    ('3','2'),
    ('3','11'),
	('4','10'),
    ('4','5'),
    ('4','15'),
    ('4','4'),
    ('5', '1'),
    ('5', '2'),
    ('5', '10'),
    ('6','3'),
    ('6','10'),
    ('7', '1'),
    ('7', '2'),
    ('7', '12'),
    ('8', '2'),
    ('8', '5'),
    ('8', '8'),
    ('9', '10'),
    ('9', '14'),
    ('9', '16');
    
    -------------------------
    
    INSERT INTO `filmeDB`.`Função`
    -- SELECT * FROM `filmeDB`.`Função`;
(IDFunção,Designação)   
VALUES
    ('1','Diretor'),
    ('2','Figurinista'),
    ('3','Compositor'),
    ('4','Técnico de Efeitos Especiais'),
    ('5','Roteirista');
    
    
     INSERT INTO `filmeDB`.`User`
     -- SELECT * FROM `filmeDB`.`User`;
(Username,email,nível,PalavraChave)  
VALUES
    ('MaryC','maryc@gmail.com','newbie','mary'),
    ('AndyW','andyW@hotmail.com','big fan','andy'),
    ('kelinhaKosta','kelinhaKosta@gmail.com','active','kelinha'),
    ('CatarinaSantejo','catSantejo@hotmail.com','newbie','catarina32'),
    ('JosieD','josie@outlook.com','active','josie'),
    ('HelenR','helenR@hotmail.com','newbie','helen123'),
    ('RichardE','richardE@gmail.com','newbie','richardM'),
    ('PaulP','paulp@gmail.com','newbie','paulnoC'),
    ('RossT','rosst@outlook.com','active','rossie'),
    ('jamesQ','jamesquinn@hotmail.com','newbie','jamess'); 
    



INSERT INTO `filmeDB`.`Review`
     -- SELECT * FROM `filmeDB`.`Review`;
(IDReview,dataReview,Comentario,Rating,IDFilme,UserName)
VALUES
('1','2011-11-14','Probably the best French film for years!','10','9','MaryC'),
('2','2018-01-01','Boring, shallow and overrated...','2','8','PaulP'),
('3','2005-11-25','"Gladiator" brought a poetic vision in a new and very cinematically richly way...','9','5','kelinhaKosta'),
('4','2001-06-13','Great Story but Far From Historical.','6','5','PaulP'),
('5','2018-07-03','Despite a lot of plot flaws and conveniences, this really is one of the best films ever made.','10','1','JosieD'),
('6','2020-01-12','An original dark comedy about class struggles.','8','4','HelenR'),
('7','2020-01-04','Too hyped, not too bad.','6','4','PaulP'),
('8','2007-05-18','Marion is spectacular but the film is needlessly melodramatic...','7','9','jamesQ'),
('9','1998-08-02','The closest thing to poetic perfection Hollywood has ever produced.','10','2','AndyW'),
('10','2020-11-07','Amber heard did a terrible acting job and should be fired.','1','3','HelenR'),
('11','2019-01-06','The most overrated generic superhero film you will ever see.','4','3','RossT'),
('12','2016-03-30','A tender and accurate portrayal ','8','6','CatarinaSantejo'),
('13','2016-10-29','Ridiculously overrated, with plot holes that are impossible to ignore.','4','6','RichardE'),
('14','2010-07-15','Sci-fi perfection. A truly mesmerizing film.','8','7','MaryC');

------------

INSERT INTO `filmeDB`.`Prémio`
     -- SELECT * FROM `filmeDB`.`Prémio`;
     
(IDPrémio,Categoria,Nome)  
VALUES
('1','Melhor Filme','Oscar'),
('2','Melhor Filme Estrangeiro','Oscar'),
('3','Melhor Diretor','Oscar'),
('4','Melhor Ator Principal ','Oscar'),
('5','Melhor Ator Coadjuvante','Oscar'),
('6','Melhor Atriz Principal ','Oscar'),
('7','Melhor Atriz Coadjuvante','Oscar'),
('8','Melhor Roteiro Original','Oscar'),
('9','Melhor Trilha Sonora','Oscar'),
('10','Melhor Figurino','Oscar'),
('11','Melhores Efeitos Especiais','Oscar'),
('12','Honorário' ,'Oscar'),
('13','Melhor Filme de Animação','Oscar'),
('14','Melhor Ator Principal','Cesar'),
('15','Melhor Filme','Cesar'),
('16','Melhor realizador','Cesar');


    INSERT INTO `filmeDB`.`Pessoa`
     -- SELECT * FROM `filmeDB`.`pessoa`;
(IDPessoa,Nome,DataDeNascimento,Género)
VALUES
('1','Angelina Jolie','1975-06-04','F'),
('2','Walt Disney','1901-12-05','M'),
('3','James Cameron','1954-08-16','M'),
('4','Kate Winslet','1975-10-05','F'),
('5','Gloria Stuart','1910-07-04','F'),
('6','Deborah Lynn Scott','1954-10-29','F'),
('7','Robert Legato','1956-05-06','M'),
('8','James Horner','1953-08-14','M'),
('9','Morgan Freeman','1937-07-01','M'),
('10','Frank Darabont','1959-01-28','M'),
('11','Leonardo DiCaprio','1974-11-11','M'),
('12','Thomas Newman','1955-10-20','M'),
('13','Tim Robbins','1958-10-16','M'),
('14','Bob Gunton','1945-11-15','M'),
('15','Bob Williams','1974-02-13','M'),
('16','Elizabeth McBride','1995-05-17','F'),
('17','James Wan','1977-02-27','M'),
('18','David Leslie Johnson-McGoldrick ','1942-10-05','M'),
('19','Kym Barrett','1965-10-11','F'),
('20','Rupert Gregson-Williams','1966-11-12','M'),
('21','Blair Berens','1995-06-28','F'),
('22','Jason Momoa','1979-08-01','M'),
('23','Amber Heard','1986-04-22','F'),
('24','Willem Dafoe','1955-07-22','M'),
('25','Nicole Kidman','1967-06-20','F'),
('26','Bong Joon Ho','1967-06-20','M'),
('27','Se-yeon Choi','1967-06-20','F'),
('28','Jaeil Jung','1967-06-20','M'),
('29','Hyo-kyun Hwang','1976-05-26','M'),
('30','Song Kang Ho','1967-02-25','M'),
('31','Lee Sun Kyun','1975-03-02','M'),
('32','Cho Yeo Jeong','1981-02-10','F'),
('33','Choi Woo Shik','1990-03-26','M'),
('34','Ellen Mirojnick','1949-07-07','F'),
('35','Gabriel Yared','1949-11-07','M'),
('36','Jennifer C. Bell','1964-08-26','F'),
('37','Mélanie Laurent','1983-02-21','F'),
('38','Brad Pitt','1963-12-18','M'),
('39','Russell Crowe','1964-04-07','M'),
('40','Joaquin Phoenix','1974-10-28','M'),
('41','Ridley Scott','1937-11-30','M'),
('42','Janty Yates','1950-05-26','F'),
('43','Hans Zimmer','1957-07-12','M'),
('44','John Nelson','1941-12-06','M'),
('45','David Franzoni','1947-03-04','M'),
('46','Chris Corbould', '1958-03-05','M'),
('47','Christopher Nolan','1970-07-30','M'),
('48','Jeffrey Kurland','1952-03-10','M'),
('49','Marion Cotillard','1975-09-30','F'),
('50','Ellen Page','1987-02-21','F'),
('51','Amy Poehler','1971-09-16','F'),
('52','Phyllis Smith','1951-07-10','F'),
('53','Kaitlyn Dias','1999-05-11','F'),
('54','Pete Docter','1968-10-09','M'), 
('55','Michael Giacchino','1967-10-10','M'), 
('56','Frank Aalbers',NULL,'M'),
('57','Emmanuelle Seigner','1966-06-22','F'),
('58','Gérard Depardieu','1948-12-27','M'),
('59','Olivier Dahan','1967-06-26','M'),
('60','Isabelle Sobelman', NULL,'F'),
('61','Christopher Gunning','1944-08-05','M'), 
('62','Marit Allen','1941-09-17','F'),
('63','Jan Holub','1983-05-03','M');



INSERT INTO `filmedb`.`FilmePrémioPessoa`
-- SELECT * FROM `filmeDB`.`FilmePrémioPessoa`;
(TableID,FilmeID,PrémioID,PessoaID,Ano,Vencedor)
VALUES
('1', NULL,'12','2','1932','Sim'),
('2','1','1',NULL,'1998','Sim'),
('3','1','3','3','1998','Sim'),
('4','1','6','4','1998','Não'),
('5','1','7','5','1998','Não'),
('6','1','9','8','1998','Sim'),
('7','1','10','6','1998','Sim'),
('8','1','11','7','1998','Sim'),
('9','2','1',NULL,'1995','Não'),
('10','2','4','9','1995','Não'),
('11','2','8','10','1995','Não'),
('12','2','9','12','1995','Não'),
('13','4','1',NULL,'2020','Sim'),
('14','4','8','26','2020','Sim'),
('15','4','3','26','2020','Sim'),
('16','4','2',NULL,'2020','Sim'),
('17','6','1',NULL,'2001','Sim'),
('18','6','4','39','2001','Sim'),
('19','6','10','42','2001','Sim'),
('20','6','11','44','2001','Sim'),
('21','6','5','40','2001','Não'),
('22','6','3','41','2001','Não'),
('23','6','8','45','2001','Não'),
('24','6','9','43','2001','Não'),
('25','7','11','46','2011','Sim'),
('26','7','1',NULL,'2011','Não'),
('27','7','8','47','2011','Não'),
('28','7','9','43','2011','Não'),
('29','8','13',NULL,'2016','Sim'),
('30','8','8','54','2016','Não'),
('31','9','6','49','2008','Sim'),
('32','9','10','62','2008','Não');


	INSERT INTO `filmedb`.`FunçãoPessoaFilme`
    -- SELECT * FROM `filmeDB`.`FunçãoPessoaFilme`;
(IDPessoa,IDFunção,IDFilme,Salário)
VALUES
('3','1','1','200000'),
('6','2','1',NULL),
('7','4','1',NULL),
('8','3','1',NULL),
('3','5','1','200000'),
('10','1','2','16000'),
('12','3','2',NULL),
('15','4','2',NULL),
('16','2','2',NULL),
('10','5','2','16000'),
('17','1','3','98000'),
('19','2','3','9500'),
('20','3','3',NULL),
('21','4','3',NULL),
('18','5','3','98000'),
('26','1','4','100000'),
('27','2','4',NULL),
('28','3','4','9000'),
('29','4','4',NULL),
('26','5','4','100000'),
('1','1','5','110000'),
('34','2','5',NULL),
('35','3','5','11000'),
('36','4','5','9500'),
('1','5','5','110000'),
('41','1','6',NULL),
('42','2','6',NULL),
('43','3','6','15000'),
('44','4','6',NULL),
('45','5','6',NULL),
('47','1','7',NULL),
('48','2','7',NULL),
('43','3','7','165000'),
('46','4','7',NULL),
('47','5','7',NULL),
('54','1','8','15050'),
('55','3','8',NULL),
('56','4','8',NULL),
('54','5','8',NULL),
('59','1','9','12000'),
('62','2','9',NULL),
('61','3','9',NULL),
('63','4','9',NULL),
('60','5','9',NULL);


	INSERT INTO `filmedb`.`filmePessoa`
    -- SELECT * FROM `filmeDB`.`filmePessoa`;
(IDFilme,IDPessoa,Protagonismo,Personagem,Salário)
VALUES
('1','4','Principal','Rose Dewitt Bukater','1000000'),
('1','5','Secundária','Rose Dewitt Bukater','50000'),
('1','11','Principal','Jack Dawson','20000000'),
('2','9','Principal','Ellis Boyd Redding','2000000'),
('2','13','Principal','Andy Dufresne','300000'),
('2','14','Secundária','Warden Norton','50000'),
('3','22','Principal','Arthur','800000'),
('3','23','Principal','Mera','500000'),
('3','24','Secundária','Vulko','100000'),
('3','25','Secundária','Atlanna','100000'),
('4','30','Principal','Ki Taek','620000'),
('4','31','Secundária','Dong Ik','400000'),
('4','32','Principal','Yeon Kyo','550000'),
('4','33','Secundária', 'Ki Woo','360000'),
('5','1','Principal','Vanessa','5000000'),
('5','37','Secundária','Lea', '200000'),
('5','38','Principal','Roland','2000000'),
('6','39','Principal','Maximus','600000'),
('6','40','Secundária','Commodus','400000'),
('7','11','Principal','Cobb','30000000'),
('7','49','Secundária','Mal','300000'),
('7','50','Secundária','Ariadne','500000'),
('8','51','Principal','Joy','100000'),
('8','52','Secundária','Sadness','50000'),
('8','53','Principal','Riley','20000'),
('9','49','Principal','Edith Piaf','800000'),
('9','57','Secundária','Titine','60000'),
('9','58','Secundária','Louis Leplée','50000');




    
