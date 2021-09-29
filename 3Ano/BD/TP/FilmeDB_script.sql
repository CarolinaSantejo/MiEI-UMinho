-- -----------------------------------------------------
-- Schema filmeDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `filmeDB` DEFAULT CHARACTER SET utf8 ;
USE `filmeDB` ;
-- DROP SCHEMA `filmeDB`~
-- -----------------------------------------------------
-- Table `filmeDB`.`Filme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`Filme` (
  `IDFilme` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Custo` FLOAT NOT NULL,
  `País` VARCHAR(20) NOT NULL,
  `Língua` VARCHAR(20) NOT NULL,
  `Duração` TIME NULL,
  `Receita` FLOAT NOT NULL,
  `PG` INT NOT NULL,
  `Descrição` TINYTEXT NOT NULL,
  `DataEstreia` DATE NOT NULL,
  PRIMARY KEY (`IDFilme`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`Género`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`Género` (
  `IDGenero` INT NOT NULL,
  `Nome` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`IDGenero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`FilmeGenero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`FilmeGenero` (
  `FilmeID` INT NOT NULL,
  `GeneroID` INT NOT NULL,
  PRIMARY KEY (`FilmeID`, `GeneroID`),
  INDEX `fk_Filme_has_Género_Género1_idx` (`GeneroID` ASC) VISIBLE,
  INDEX `fk_Filme_has_Género_Filme_idx` (`FilmeID` ASC) VISIBLE,
  CONSTRAINT `fk_Filme_has_Género_Filme`
    FOREIGN KEY (`FilmeID`)
    REFERENCES `filmeDB`.`Filme` (`IDFilme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Filme_has_Género_Género1`
    FOREIGN KEY (`GeneroID`)
    REFERENCES `filmeDB`.`Género` (`IDGenero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`User` (
  `UserName` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `Nível` VARCHAR(10) NOT NULL,
  `PalavraChave` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`UserName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`Review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`Review` (
  `IDReview` INT NOT NULL,
  `DataReview` DATETIME NOT NULL,
  `Comentario` TINYTEXT NOT NULL,
  `Rating` INT NOT NULL,
  `IDFilme` INT NOT NULL,
  `UserName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IDReview`),
  INDEX `fk_Review_Filme1_idx` (`IDFilme` ASC) VISIBLE,
  INDEX `fk_Review_User1_idx` (`UserName` ASC) VISIBLE,
  CONSTRAINT `fk_Review_Filme1`
    FOREIGN KEY (`IDFilme`)
    REFERENCES `filmeDB`.`Filme` (`IDFilme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Review_User1`
    FOREIGN KEY (`UserName`)
    REFERENCES `filmeDB`.`User` (`UserName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`Função`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`Função` (
  `IDFunção` INT NOT NULL,
  `Designação` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`IDFunção`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`Pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`Pessoa` (
  `IDPessoa` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `DataDeNascimento` DATE NULL,
  `Género` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`IDPessoa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`FunçãoPessoaFilme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`FunçãoPessoaFilme` (
  `IDPessoa` INT NOT NULL,
  `IDFunção` INT NOT NULL,
  `IDFilme` INT NOT NULL,
  `Salário` FLOAT NULL,
  PRIMARY KEY (`IDPessoa`, `IDFunção`, `IDFilme`),
  INDEX `fk_Pessoa_has_Função_Função1_idx` (`IDFunção` ASC) VISIBLE,
  INDEX `fk_Pessoa_has_Função_Pessoa1_idx` (`IDPessoa` ASC) VISIBLE,
  INDEX `fk_FunçãoPessoaFilme_Filme1_idx` (`IDFilme` ASC) VISIBLE,
  CONSTRAINT `fk_Pessoa_has_Função_Pessoa1`
    FOREIGN KEY (`IDPessoa`)
    REFERENCES `filmeDB`.`Pessoa` (`IDPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pessoa_has_Função_Função1`
    FOREIGN KEY (`IDFunção`)
    REFERENCES `filmeDB`.`Função` (`IDFunção`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FunçãoPessoaFilme_Filme1`
    FOREIGN KEY (`IDFilme`)
    REFERENCES `filmeDB`.`Filme` (`IDFilme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`FilmePessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`FilmePessoa` (
  `IDFilme` INT NOT NULL,
  `IDPessoa` INT NOT NULL,
  `Protagonismo` VARCHAR(45) NOT NULL,
  `Personagem` VARCHAR(45) NOT NULL,
  `Salário` FLOAT NULL,
  PRIMARY KEY (`IDFilme`, `IDPessoa`),
  INDEX `fk_Filme_has_Pessoa_Pessoa1_idx` (`IDPessoa` ASC) VISIBLE,
  INDEX `fk_Filme_has_Pessoa_Filme1_idx` (`IDFilme` ASC) VISIBLE,
  CONSTRAINT `fk_Filme_has_Pessoa_Filme1`
    FOREIGN KEY (`IDFilme`)
    REFERENCES `filmeDB`.`Filme` (`IDFilme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Filme_has_Pessoa_Pessoa1`
    FOREIGN KEY (`IDPessoa`)
    REFERENCES `filmeDB`.`Pessoa` (`IDPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`Prémio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`Prémio` (
  `IDPrémio` INT NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IDPrémio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `filmeDB`.`FilmePrémioPessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `filmeDB`.`FilmePrémioPessoa` (
  `TableID` INT NOT NULL,
  `FilmeID` INT NULL,
  `PrémioID` INT NOT NULL,
  `PessoaID` INT NULL,
  `Ano` INT NOT NULL,
  `Vencedor` VARCHAR(5) NOT NULL,
  INDEX `fk_Filme_has_Prémio_Prémio1_idx` (`PrémioID` ASC) VISIBLE,
  INDEX `fk_Filme_has_Prémio_Filme1_idx` (`FilmeID` ASC) VISIBLE,
  INDEX `fk_FilmePrémioPessoa_Pessoa1_idx` (`PessoaID` ASC) VISIBLE,
  PRIMARY KEY (`TableID`),
  CONSTRAINT `fk_Filme_has_Prémio_Filme1`
    FOREIGN KEY (`FilmeID`)
    REFERENCES `filmeDB`.`Filme` (`IDFilme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Filme_has_Prémio_Prémio1`
    FOREIGN KEY (`PrémioID`)
    REFERENCES `filmeDB`.`Prémio` (`IDPrémio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FilmePrémioPessoa_Pessoa1`
    FOREIGN KEY (`PessoaID`)
    REFERENCES `filmeDB`.`Pessoa` (`IDPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

