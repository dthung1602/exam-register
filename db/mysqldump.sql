-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: examreg
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.19.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ACCOUNT`
--

DROP TABLE IF EXISTS `ACCOUNT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACCOUNT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(128) NOT NULL,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACCOUNT`
--

LOCK TABLES `ACCOUNT` WRITE;
/*!40000 ALTER TABLE `ACCOUNT` DISABLE KEYS */;
INSERT INTO `ACCOUNT` VALUES (1,'assistant','$31$8$s0h42VmvAcxoFuJU8XLe-q5RiNrlhSZB76gd7sRFxLk','Thi Thuy Trang','Nguyen'),(2,'lecturer1','$31$8$-pnzkySN4-q4iRtGjyTo_exEn9IU3qjvtMcR3cXUyJ8','Manuel','Clavel'),(3,'lecturer2','$31$8$RzGn3jf5CT4iLNxpWsQQzRKb7gKaIyYKt0Blol2BrqM','Christian','Baum'),(4,'lecturer3','$31$8$vYdMZ_ZzAjS8PLfCIlYsLCA4m8ZG8z_sQAXC6X3LEvo','Hai Anh','Tran'),(5,'student1','$31$8$ajjvfFcdTZi_vNBhn5ilFdgGTAbGzZxZO6JzFfx0qII','Ho Tat Dat','Nguyen'),(6,'student2','$31$8$f2Ex73_MF9YWLvMMrAzAFK1sPpge5czbyrNWGY1-mWU','Tuan Hung','Vu'),(7,'student3','$31$8$GjsVXHaKvRptSLNBoKCEWkWTBBuGnhNQHBkU2rADtZA','Thanh Hung','Duong'),(8,'student4','$31$8$PtLybRCEryGybHIV0MKE3OVCoUpqbu4RBm8tEEhT9Lg','Truong Thanh Hung','Nguyen'),(9,'student5','$31$8$DtR1AO8gK6TvMbb1pFCqs1PGVtnPtnNNO-OYwU2D2s0','Vinh Long','Huynh'),(10,'student6','$31$8$LC_BLw_N5WTsIn00Shl4I_O1OYCIHEmtwf_Vr5IIRlY','Thanh Long','Nguyen');
/*!40000 ALTER TABLE `ACCOUNT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ASSISTANT`
--

DROP TABLE IF EXISTS `ASSISTANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ASSISTANT` (
  `account` int(11) NOT NULL,
  PRIMARY KEY (`account`),
  CONSTRAINT `ASSISTANT_ibfk_1` FOREIGN KEY (`account`) REFERENCES `ACCOUNT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ASSISTANT`
--

LOCK TABLES `ASSISTANT` WRITE;
/*!40000 ALTER TABLE `ASSISTANT` DISABLE KEYS */;
INSERT INTO `ASSISTANT` VALUES (1);
/*!40000 ALTER TABLE `ASSISTANT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ENROLL`
--

DROP TABLE IF EXISTS `ENROLL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ENROLL` (
  `student` int(11) NOT NULL,
  `module` int(11) NOT NULL,
  PRIMARY KEY (`student`,`module`),
  KEY `module` (`module`),
  CONSTRAINT `ENROLL_ibfk_1` FOREIGN KEY (`student`) REFERENCES `STUDENT` (`account`) ON DELETE CASCADE,
  CONSTRAINT `ENROLL_ibfk_2` FOREIGN KEY (`module`) REFERENCES `MODULE` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ENROLL`
--

LOCK TABLES `ENROLL` WRITE;
/*!40000 ALTER TABLE `ENROLL` DISABLE KEYS */;
INSERT INTO `ENROLL` VALUES (5,1),(6,1),(7,1),(8,1),(10,1),(6,2),(7,2),(8,2),(5,3),(8,3),(6,4),(7,4),(8,4);
/*!40000 ALTER TABLE `ENROLL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAM`
--

DROP TABLE IF EXISTS `EXAM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAM` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` int(11) NOT NULL,
  `date` date NOT NULL,
  `deadline` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `module` (`module`,`date`),
  CONSTRAINT `EXAM_ibfk_1` FOREIGN KEY (`module`) REFERENCES `MODULE` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAM`
--

LOCK TABLES `EXAM` WRITE;
/*!40000 ALTER TABLE `EXAM` DISABLE KEYS */;
INSERT INTO `EXAM` VALUES (1,1,'2019-05-07','2019-05-03','13:00:00','16:00:00'),(2,2,'2019-05-17','2019-05-15','10:00:00','11:30:00'),(3,4,'2019-05-31','2019-05-28','14:00:00','16:00:00');
/*!40000 ALTER TABLE `EXAM` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`examreguser`@`localhost`*/ /*!50003 TRIGGER CHECK_INSERT_EXAM
    BEFORE INSERT
    ON EXAM
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.deadline, NEW.date);
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`examreguser`@`localhost`*/ /*!50003 TRIGGER CHECK_UPDATE_EXAM
    BEFORE INSERT
    ON EXAM
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.deadline, NEW.date);
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `EXAM_REG`
--

DROP TABLE IF EXISTS `EXAM_REG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAM_REG` (
  `student` int(11) NOT NULL,
  `exam` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`student`,`exam`),
  KEY `exam` (`exam`),
  CONSTRAINT `EXAM_REG_ibfk_1` FOREIGN KEY (`student`) REFERENCES `STUDENT` (`account`) ON DELETE CASCADE,
  CONSTRAINT `EXAM_REG_ibfk_2` FOREIGN KEY (`exam`) REFERENCES `EXAM` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAM_REG`
--

LOCK TABLES `EXAM_REG` WRITE;
/*!40000 ALTER TABLE `EXAM_REG` DISABLE KEYS */;
/*!40000 ALTER TABLE `EXAM_REG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LECTURER`
--

DROP TABLE IF EXISTS `LECTURER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LECTURER` (
  `account` int(11) NOT NULL,
  PRIMARY KEY (`account`),
  CONSTRAINT `LECTURER_ibfk_1` FOREIGN KEY (`account`) REFERENCES `ACCOUNT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LECTURER`
--

LOCK TABLES `LECTURER` WRITE;
/*!40000 ALTER TABLE `LECTURER` DISABLE KEYS */;
INSERT INTO `LECTURER` VALUES (2),(3),(4);
/*!40000 ALTER TABLE `LECTURER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MODULE`
--

DROP TABLE IF EXISTS `MODULE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MODULE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(8) NOT NULL,
  `semester` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `semester` (`semester`),
  CONSTRAINT `MODULE_ibfk_1` FOREIGN KEY (`semester`) REFERENCES `SEMESTER` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MODULE`
--

LOCK TABLES `MODULE` WRITE;
/*!40000 ALTER TABLE `MODULE` DISABLE KEYS */;
INSERT INTO `MODULE` VALUES (1,'Software Engineering Design','SWED',2),(2,'Programming Exercise ','ProEx',2),(3,'Computer Network','CN',1),(4,'Distributed System','DS',2);
/*!40000 ALTER TABLE `MODULE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEMESTER`
--

DROP TABLE IF EXISTS `SEMESTER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEMESTER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` date NOT NULL,
  `end` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEMESTER`
--

LOCK TABLES `SEMESTER` WRITE;
/*!40000 ALTER TABLE `SEMESTER` DISABLE KEYS */;
INSERT INTO `SEMESTER` VALUES (1,'2018-08-01','2019-01-01'),(2,'2019-02-01','2019-07-01');
/*!40000 ALTER TABLE `SEMESTER` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`examreguser`@`localhost`*/ /*!50003 TRIGGER CHECK_INSERT_SEMESTER
    BEFORE INSERT
    ON SEMESTER
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.start, NEW.end);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`examreguser`@`localhost`*/ /*!50003 TRIGGER CHECK_UPDATE_SEMESTER
    BEFORE UPDATE
    ON SEMESTER
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.start, NEW.end);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `SESSION`
--

DROP TABLE IF EXISTS `SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SESSION` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` int(11) NOT NULL,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `module` (`module`,`date`,`start`),
  CONSTRAINT `SESSION_ibfk_1` FOREIGN KEY (`module`) REFERENCES `MODULE` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SESSION`
--

LOCK TABLES `SESSION` WRITE;
/*!40000 ALTER TABLE `SESSION` DISABLE KEYS */;
INSERT INTO `SESSION` VALUES (1,2,'2019-05-07','09:00:00','11:45:00'),(2,2,'2019-05-07','13:00:00','16:00:00'),(3,2,'2019-05-08','09:00:00','11:45:00'),(4,1,'2019-05-13','09:00:00','11:45:00'),(5,1,'2019-05-13','13:00:00','16:00:00'),(6,1,'2019-05-14','09:00:00','11:45:00'),(7,1,'2019-05-14','13:00:00','16:00:00'),(8,3,'2018-11-01','09:00:00','11:45:00'),(9,3,'2018-11-02','09:00:00','11:45:00'),(10,3,'2018-12-03','13:00:00','16:00:00'),(11,4,'2019-04-22','09:00:00','11:45:00'),(12,4,'2019-04-23','09:00:00','11:45:00'),(13,4,'2019-05-07','13:00:00','16:00:00'),(14,4,'2019-05-08','09:00:00','11:45:00'),(15,4,'2019-05-08','13:00:00','16:00:00'),(16,4,'2019-05-07','09:00:00','11:45:00');
/*!40000 ALTER TABLE `SESSION` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`examreguser`@`localhost`*/ /*!50003 TRIGGER CHECK_INSERT_SESSION
    BEFORE INSERT
    ON SESSION
    FOR EACH ROW
BEGIN
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
    CALL CHECK_SESSION_OVERLAP(NEW.date, NEW.start, NEW.end, NEW.module, -1);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`examreguser`@`localhost`*/ /*!50003 TRIGGER CHECK_UPDATE_SESSION
    BEFORE UPDATE
    ON SESSION
    FOR EACH ROW
BEGIN
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
    CALL CHECK_SESSION_OVERLAP(NEW.date, NEW.start, NEW.end, NEW.module, NEW.id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `SIGN`
--

DROP TABLE IF EXISTS `SIGN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SIGN` (
  `student` int(11) NOT NULL,
  `session` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`student`,`session`),
  KEY `session` (`session`),
  CONSTRAINT `SIGN_ibfk_1` FOREIGN KEY (`student`) REFERENCES `STUDENT` (`account`) ON DELETE CASCADE,
  CONSTRAINT `SIGN_ibfk_2` FOREIGN KEY (`session`) REFERENCES `SESSION` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SIGN`
--

LOCK TABLES `SIGN` WRITE;
/*!40000 ALTER TABLE `SIGN` DISABLE KEYS */;
/*!40000 ALTER TABLE `SIGN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STUDENT`
--

DROP TABLE IF EXISTS `STUDENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STUDENT` (
  `code` char(8) DEFAULT NULL,
  `account` int(11) NOT NULL,
  PRIMARY KEY (`account`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `STUDENT_ibfk_1` FOREIGN KEY (`account`) REFERENCES `ACCOUNT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STUDENT`
--

LOCK TABLES `STUDENT` WRITE;
/*!40000 ALTER TABLE `STUDENT` DISABLE KEYS */;
INSERT INTO `STUDENT` VALUES ('11111',5),('22222',6),('33333',8),('44444',9),('55555',7),('66666',10);
/*!40000 ALTER TABLE `STUDENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TEACH`
--

DROP TABLE IF EXISTS `TEACH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TEACH` (
  `module` int(11) NOT NULL,
  `lecturer` int(11) NOT NULL,
  PRIMARY KEY (`module`,`lecturer`),
  KEY `lecturer` (`lecturer`),
  CONSTRAINT `TEACH_ibfk_1` FOREIGN KEY (`module`) REFERENCES `MODULE` (`id`) ON DELETE CASCADE,
  CONSTRAINT `TEACH_ibfk_2` FOREIGN KEY (`lecturer`) REFERENCES `LECTURER` (`account`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TEACH`
--

LOCK TABLES `TEACH` WRITE;
/*!40000 ALTER TABLE `TEACH` DISABLE KEYS */;
INSERT INTO `TEACH` VALUES (1,2),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `TEACH` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-07 10:58:42
