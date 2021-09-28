CREATE DATABASE NewsBookDB
GO


CREATE TABLE NewsBookDB.dbo.Country (
  [IdCountry] VARCHAR(10) NOT NULL,
  [Nome] VARCHAR(45) NOT NULL,
  PRIMARY KEY ([IdCountry])
)


CREATE TABLE NewsBookDB.dbo.Utilizador (
  [UserName] VARCHAR(30) NOT NULL,
  [Email] VARCHAR(45) NOT NULL UNIQUE,
  [Password] VARCHAR(45) NOT NULL,
  [Nome] VARCHAR(45) NOT NULL,
  [Cidade] VARCHAR(45) NULL,
  [Fotografia] VARCHAR(45) NULL,
  [Country_IdCountry] VARCHAR(10) NOT NULL,
  PRIMARY KEY ([UserName]),
  CONSTRAINT [fk_Utilizador_Country1] 
    FOREIGN KEY ([Country_IdCountry])
    REFERENCES Country([IdCountry])
)



CREATE TABLE NewsBookDB.dbo.Categoria (
  [IdCategoria] INT NOT NULL,
  [Nome] VARCHAR(30) NOT NULL,
  PRIMARY KEY ([IdCategoria])
)




CREATE TABLE NewsBookDB.dbo.Utilizador_Categoria (
  [UserName] VARCHAR(30) NOT NULL,
  [IdCategoria] INT NOT NULL,
  PRIMARY KEY ([UserName], [IdCategoria]),
  CONSTRAINT [fk_Utilizador_has_Categoria_Utilizador1]
    FOREIGN KEY ([UserName])
    REFERENCES Utilizador([UserName]),
  CONSTRAINT fk_Utilizador_has_Categoria_Categoria1
    FOREIGN KEY ([IdCategoria])
    REFERENCES [Categoria]([IdCategoria])
)



CREATE TABLE NewsBookDB.dbo.Jornalista (
  [UserNameJornalista] VARCHAR(30) NOT NULL,
  [Biografia] VARCHAR(max) NULL,
  [LinkedIn] VARCHAR(45) NULL,
  PRIMARY KEY ([UserNameJornalista]),
  CONSTRAINT [fk_Utilizador_UserName1]
    FOREIGN KEY ([UserNameJornalista])
    REFERENCES Utilizador([UserName])
)




CREATE TABLE NewsBookDB.dbo.Publicacao (
  [IdPublicacao] INT NOT NULL,
  [Titulo] VARCHAR(max) NOT NULL,
  [Data] DATETIME2(0) NOT NULL,
  [Descricao] VARCHAR(max) NULL,
  [Imagem] VARCHAR(max) NULL,
  PRIMARY KEY ([IdPublicacao])
)




CREATE TABLE NewsBookDB.dbo.Noticia (
  [Url] VARCHAR(max) NOT NULL,
  [Autor] VARCHAR(45) NULL,
  [IdPublicacao] INT NOT NULL
  PRIMARY KEY ([IdPublicacao]),
  CONSTRAINT [fk_Noticia_Publicacao1]
    FOREIGN KEY ([IdPublicacao])
    REFERENCES Publicacao([IdPublicacao])
)



CREATE TABLE NewsBookDB.dbo.ArtigoOpiniao (
  [UserNameJornalista] VARCHAR(30) NOT NULL,
  [Conteudo] VARCHAR(max) NOT NULL,
  [IdPublicacao] INT NOT NULL,
  PRIMARY KEY ([IdPublicacao]),
  CONSTRAINT [fk_ArtigoOpiniao_Jornalista1]
    FOREIGN KEY ([UserNameJornalista])
    REFERENCES Jornalista([UserNameJornalista]),
  CONSTRAINT [fk_ArtigoOpiniao_Publicacao1]
    FOREIGN KEY ([IdPublicacao])
    REFERENCES Publicacao([IdPublicacao])
)



CREATE TABLE NewsBookDB.dbo.Comentario (
  [UserName] VARCHAR(30) NOT NULL,
  [IdPublicacao] INT NOT NULL,
  [Texto] VARCHAR(max) NOT NULL,
  [Data] DATETIME2(0) NOT NULL,
  PRIMARY KEY ([UserName], [IdPublicacao]),
  CONSTRAINT [fk_Utilizador_has_Publicacao_Utilizador1]
    FOREIGN KEY ([UserName])
    REFERENCES Utilizador([UserName]),
  CONSTRAINT [fk_Utilizador_has_Publicacao_Publicacao1]
    FOREIGN KEY ([IdPublicacao])
    REFERENCES Publicacao([IdPublicacao])
)



CREATE TABLE NewsBookDB.dbo.Utilizador_Jornalista (
  [UserName] VARCHAR(30) NOT NULL,
  [UserNameJornalista] VARCHAR(30) NOT NULL,
  PRIMARY KEY ([UserName], [UserNameJornalista]),
  CONSTRAINT [fk_Utilizador_has_Jornalista_Utilizador1]
    FOREIGN KEY ([UserName])
    REFERENCES Utilizador([UserName]),
  CONSTRAINT [fk_Utilizador_has_Jornalista_Jornalista1]
    FOREIGN KEY ([UserNameJornalista])
    REFERENCES Jornalista([UserNameJornalista])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)




CREATE TABLE NewsBookDB.dbo.Notificacao (
  [Data] DATETIME2(0) NOT NULL,
  [IdPublicacao] INT NOT NULL,
  [UserName] VARCHAR(30) NOT NULL,
  PRIMARY KEY ([IdPublicacao], [UserName]),
  CONSTRAINT [fk_Notificacao_Publicacao1]
    FOREIGN KEY ([IdPublicacao])
    REFERENCES Publicacao ([IdPublicacao]),
  CONSTRAINT [fk_Notificacao_Utilizador1]
    FOREIGN KEY ([UserName])
    REFERENCES Utilizador ([UserName])
)


CREATE TABLE NewsBookDB.dbo.Utilizador_Noticia (
  [UserName] VARCHAR(30) NOT NULL,
  [IdPublicacao] INT NOT NULL,
  [Data] DATETIME2(0) NOT NULL,
  PRIMARY KEY ([UserName], [IdPublicacao]),
  CONSTRAINT [fk_Utilizador_has_Noticia_Utilizador1]
    FOREIGN KEY ([UserName])
    REFERENCES Utilizador ([UserName]),
  CONSTRAINT [fk_Utilizador_has_Noticia_Noticia1]
    FOREIGN KEY ([IdPublicacao])
    REFERENCES Noticia ([IdPublicacao])
)