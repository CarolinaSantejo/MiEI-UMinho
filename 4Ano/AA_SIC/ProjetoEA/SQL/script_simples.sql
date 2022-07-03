--
-- TABLE STRUCTURE FOR: Advertisement
--



--
-- TABLE STRUCTURE FOR: Advertisement_species
--



--
-- TABLE STRUCTURE FOR: Client
--



--
-- TABLE STRUCTURE FOR: Animal
--



--
-- TABLE STRUCTURE FOR: Petsitter
--




--
-- TABLE STRUCTURE FOR: Price
--
INSERT INTO Price ([id],[opt],[price]) VALUES (1,'1-3',3), (2, '4-12', 5), (3, '12-24', 8), (4, '0-7', 10), (5, '7-15', 15), (6, '15-25', 20), (7,'25-35',25),(8,'>35',50),(9,'Per hour',6);


--
-- TABLE STRUCTURE FOR: Service
--
INSERT INTO Service VALUES (1,'Companion'),(2,'Bath_Shear'),(3,'Training'),(4,'Companion'),(5,'Training'),(6,'Training'),(7,'Companion'),(8,'Bath_Shear'),(9,'Companion'),(10,'Bath_Shear'),(11,'Bath_Shear'),(12,'Companion'),(13,'Companion'),(14,'Bath_Shear'),(15,'Training'),(16,'Companion'),(17,'Bath_Shear'),(18,'Bath_Shear'),(19,'Training'),(20,'Companion'),(21,'Bath_Shear'),(22,'Training'),(23,'Training'),(24,'Companion'),(25,'Training'),(26,'Companion'),(27,'Bath_Shear'),(28,'Bath_Shear'),(29,'Training'),(30,'Training'),(31,'Bath_Shear'),(32,'Bath_Shear'),(33,'Bath_Shear'),(34,'Bath_Shear'),(35,'Training'),(36,'Training'),(37,'Bath_Shear'),(38,'Companion'),(39,'Bath_Shear'),(40,'Companion'),(41,'Training'),(42,'Companion'),(43,'Bath_Shear'),(44,'Training'),(45,'Training'),(46,'Companion'),(47,'Training'),(48,'Companion'),(49,'Training'),(50,'Companion'),(51,'Training'),(52,'Training'),(53,'Bath_Shear'),(54,'Training'),(55,'Training'),(56,'Bath_Shear'),(57,'Training'),(58,'Bath_Shear'),(59,'Companion'),(60,'Companion'),(61,'Training'),(62,'Companion'),(63,'Companion'),(64,'Bath_Shear'),(65,'Training'),(66,'Bath_Shear'),(67,'Companion'),(68,'Companion'),(69,'Companion'),(70,'Companion'),(71,'Companion'),(72,'Companion'),(73,'Bath_Shear'),(74,'Companion'),(75,'Companion'),(76,'Companion'),(77,'Training'),(78,'Training'),(79,'Companion'),(80,'Bath_Shear'),(81,'Bath_Shear'),(82,'Bath_Shear'),(83,'Bath_Shear'),(84,'Companion'),(85,'Training'),(86,'Companion'),(87,'Training'),(88,'Training'),(89,'Bath_Shear'),(90,'Training'),(91,'Companion'),(92,'Companion'),(93,'Companion'),(94,'Training'),(95,'Bath_Shear'),(96,'Companion'),(97,'Bath_Shear'),(98,'Training'),(99,'Companion'),(100,'Bath_Shear');


--
-- TABLE STRUCTURE FOR: Service_price
--
INSERT INTO Service_Price ([Service_id],[price_id]) VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(2,6),(2,7),(2,8),(3,9);

--
-- TABLE STRUCTURE FOR: ServiceOpt
--
INSERT INTO ServiceOpt VALUES ('1-3',1),('>35',2),('4-12',3),('4-12',4),('7-15',5),('Per hour',6),('25-35',7),('>35',8),('7-15',9),('25-35',10),('25-35',11),('25-35',12),('15-25',13),('7-15',14),('>35',15),('7-15',16),('12-24',17),('Per hour',18),('4-12',19),('0-7',20),('15-25',21),('1-3',22),('12-24',23),('0-7',24),('1-3',25),('>35',26),('12-24',27),('Per hour',28),('7-15',29),('12-24',30),('1-3',31),('1-3',32),('15-25',33),('0-7',34),('15-25',35),('4-12',36),('Per hour',37),('4-12',38),('>35',39),('>35',40),('0-7',41),('0-7',42),('7-15',43),('0-7',44),('Per hour',45),('>35',46),('>35',47),('12-24',48),('>35',49),('1-3',50),('7-15',51),('12-24',52),('25-35',53),('0-7',54),('25-35',55),('0-7',56),('0-7',57),('15-25',58),('25-35',59),('7-15',60),('0-7',61),('>35',62),('25-35',63),('25-35',64),('15-25',65),('>35',66),('>35',67),('Per hour',68),('25-35',69),('12-24',70),('25-35',71),('15-25',72),('0-7',73),('Per hour',74),('1-3',75),('7-15',76),('1-3',77),('1-3',78),('>35',79),('0-7',80),('15-25',81),('7-15',82),('4-12',83),('4-12',84),('Per hour',85),('0-7',86),('Per hour',87),('4-12',88),('4-12',89),('25-35',90),('0-7',91),('7-15',92),('7-15',93),('7-15',94),('4-12',95),('15-25',96),('25-35',97),('15-25',98),('12-24',99),('25-35',100);