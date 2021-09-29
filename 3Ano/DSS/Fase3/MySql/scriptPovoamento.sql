USE `SistemaArmazem` ;
-- DELETE FROM `SistemaArmazem`.`qrCodes`;
-- SELECT * FROM `SistemaArmazem`.`qrCodes`;
INSERT INTO `SistemaArmazem`.`qrCodes`
(Codigo,Produto)
VALUES
('1','Ferrero Rocher'),
('2','Lindor'),
('3','Merci'),
('4','ChesterField Gold'),
('5','Mon Cheri'),
('6','Rafaellos'),
('7','Regina'),
('8','Guylian'),
('9','Champs Elysees'),
('10','Rondnoir');


-- DELETE FROM `SistemaArmazem`.`vertices`;
-- SELECT * FROM `SistemaArmazem`.`vertices`;
INSERT INTO `SistemaArmazem`.`vertices`
(Codigo,Designacao,Ocupacao)
VALUES
('1','Zona D',0),
('2','Canto A','0'),
('3','Canto B','0'),
('4','Canto C','0'),
('5','Canto D','0'),
('6','P1','0'),
('7','P2','0'),
('8','P3','0'),
('9','P4','0'),
('10','P5','0'),
('11','P6','0'),
('12','P7','0'),
('13','P8','0'),
('14','P9','0'),
('15','P10','0');


-- DELETE FROM `SistemaArmazem`.`arestas`;
-- SELECT * FROM `SistemaArmazem`.`arestas`;
INSERT INTO `SistemaArmazem`.`arestas`
(Codigo,Distancia,VerticeInicial,VerticeFinal)
VALUES
('1','2','1','2'),
('2','2','2','6'),
('3','4','6','7'),
('4','4','7','8'),
('5','4','8','9'),
('6','4','9','10'),
('7','2','10','4'),
('8','5','4','5'),
('9','2','5','15'),
('10','4','15','14'),
('11','4','14','13'),
('12','4','13','12'),
('13','4','12','11'),
('14','2','11','3'),
('15','5','3','2'),
('16','2','2','1'),
('17','2','6','2'),
('18','4','7','6'),
('19','4','8','7'),
('20','4','9','8'),
('21','4','10','9'),
('22','2','4','10'),
('23','5','5','4'),
('24','2','15','5'),
('25','4','14','15'),
('26','4','13','14'),
('27','4','12','13'),
('28','4','11','12'),
('29','2','3','11'),
('30','5','2','3');



-- DELETE FROM `SistemaArmazem`.`robots`;
-- SELECT * FROM `SistemaArmazem`.`robots`;
INSERT INTO `SistemaArmazem`.`robots`
(CodigoRobot,Estado,Rota,Localizacao)
VALUES
('Wall-E','0',null,'1'),
('Xiaomi','0',null,'1'),
('Etelvina','0',null,'1');


