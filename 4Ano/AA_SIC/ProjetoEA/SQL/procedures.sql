--
-- TABLE STRUCTURE FOR: TimeInterval
--
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE PROCEDURE InsertTimeInterval
AS
BEGIN
    DECLARE @i INT
    declare @start_date datetime2(0)
    declare @end_date datetime2(0)
    SET @i = 1;
    WHILE @i <= 100 BEGIN
        set @start_date = DATEADD(DAY, ABS(CHECKSUM(NEWID()) % 150), '2022-01-01')
        SET @end_date = DATEADD(HOuR, ABS(CHECKSUM(NEWID()) % 23), @start_date)
        INSERT INTO TimeInterval VALUES (@i,@end_date,@start_date)
        SET @i = @i + 1;
    END
END
GO
exec InsertTimeInterval;

--
-- TABLE STRUCTURE FOR: TimeInterval
--
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE PROCEDURE InsertRequest
AS
BEGIN
    DECLARE @i INT
    declare @start_date datetime2(0)
    declare @end_date datetime2(0)
    declare @final_price float
    declare @animal_id INT
    declare @client varchar(255)
    SET @i = 0;
    WHILE @i < 100 BEGIN
        set @start_date = DATEADD(DAY, ABS(CHECKSUM(NEWID()) % 150), '2022-01-01')
        SET @end_date = DATEADD(HOUR, ABS(CHECKSUM(NEWID()) % 23), @start_date)
        SET @final_price = FLOOR(RAND()*(80.0-5.0+5.0))+5.0;
        SET @animal_id = (select id from animal order by id asc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY)
        SET @client = (select client_id from animal where animal.id=@animal_id)
        PRINT @client
        PRINT @animal_id
        PRINT @i
        INSERT INTO Request VALUES (@i+1,@end_date,@final_price,@start_date,(select id from advertisement order by id asc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY),@animal_id,@client,(select id from ServiceOpt order by id asc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY))
        SET @i = @i + 1;
    END
END
go



--
-- TABLE STRUCTURE FOR: Advertisement_Service
--
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE PROCEDURE InsertAdvServ
AS
    DECLARE @i INT;
    DECLARE @ad INT;
    DECLARE @serv INT;
    SET @i = 0;
    WHILE @i < 100 BEGIN
        SET @ad = (select id from Advertisement order by id asc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY)
        SET @serv = (select id from Service order by id asc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY)
        PRINT @ad;
        INSERT INTO Advertisement_Service VALUES (@ad,@serv);
        SET @i = @i + 1;
    END
go



--
-- TABLE STRUCTURE FOR: Advertisement_TimeInterval
--
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE PROCEDURE InsertAdvTime
AS
    SET NOCOUNT ON;
DECLARE @i INT;
declare @advertisement_id int;
declare @time_id int;
declare @time_date datetime2(0);
    SET @i = 0;
    WHILE @i < 100 BEGIN
        select @advertisement_id = id from advertisement order by id desc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY;
        select @time_id = id as time_id from timeinterval order by id desc OFFSET @i ROWS FETCH NEXT 1 ROWS ONLY;
        select @time_date = startTime from timeinterval where id=@time_id;
        INSERT INTO Advertisement_TimeInterval VALUES (@advertisement_id,@time_id,datename(dw, @time_date));
        SET @i = @i + 1;
    END;
GO;
exec InsertAdvTime;