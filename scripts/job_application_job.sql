-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: job_application
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `ID` varchar(255) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `num` int DEFAULT NULL,
  `workMethod` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `requirment` varchar(2000) DEFAULT NULL,
  `benefit` varchar(2000) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `experiment` varchar(255) DEFAULT NULL,
  `CompanyID` varchar(255) DEFAULT NULL,
  `min_wage` int DEFAULT NULL,
  `max_wage` int DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CompanyID` (`CompanyID`),
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`CompanyID`) REFERENCES `company` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES ('JOB0001','Kế toán',2,'fullTime','Từ cao đẳng trở lên','Kế toán tổng hợp','Hiệp Hòa','abc','def','ghj','2025-05-31','2 năm trở lên','COM0001',5000000,10000000,'Không giới hạn','Chuyên Viên Kế Toán Tổng Hợp','open'),('JOB0002','Nhân viên phục vụ',2,'partTime','Từ cao đẳng trở lên','Không yêu cầu','Hiệp Hòa','abcxyz','bcasdasf','rthfghdfgh\r\nghdfgdfg\r\nfsd\r\nfd','2025-05-29','Không yêu cầu','COM0001',3000000,5000000,'18-25','Tuyển nhân viên part time','closed'),('JOB0003','Nhân viên phục vụ',1,'partTime','Từ cao đẳng trở lên','Kế toán tổng hợp','Hiệp Hòa','abcccccc\r\n- 123\r\n','ádass\r\n- 1561asd','sdd\r\n- 1548548\r\n-sadb','2025-05-31','2 năm trở lên','COM0001',0,0,'Không giới hạn','B22DCCN915','open');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-01 23:54:49
