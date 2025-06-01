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
-- Table structure for table `cv_job`
--

DROP TABLE IF EXISTS `cv_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cv_job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cv_url` varchar(255) DEFAULT NULL,
  `UserID` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `JobID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `cv_job_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv_job`
--

LOCK TABLES `cv_job` WRITE;
/*!40000 ALTER TABLE `cv_job` DISABLE KEYS */;
INSERT INTO `cv_job` VALUES (4,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748696599/eodouf0zj4e9qpimy3ry.jpg','USER0001','denied','2025-05-31','JOB0001'),(5,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748698846/dtqnnddlkcig6zw3ga0o.webp','USER0001','denied','2025-05-31','JOB0001'),(6,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748700675/mqdc22hxp2nblww9cqzd.jpg','USER0001','denied','2025-05-31','JOB0001'),(7,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748700705/mokgujr2v2ohlz87pj3s.webp','USER0001','accepted','2025-05-31','JOB0003'),(8,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748708629/j69hm2l0swsuexfbofcb.png','USER0001','accepted','2025-05-31','JOB0002'),(9,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748747463/ufd6aui6vraynqifm3j7.png','USER0001','accepted','2025-06-01','JOB0002'),(10,'https://res.cloudinary.com/dgrmbspxb/image/upload/v1748766738/xhvnlgsorprkowdssuto.jpg','USER0002','waiting','2025-06-01','JOB0002');
/*!40000 ALTER TABLE `cv_job` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-01 23:54:50
