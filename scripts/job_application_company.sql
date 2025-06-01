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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `ID` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `tax_num` varchar(255) DEFAULT NULL,
  `license_url` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES ('COM0001','Công ty ABC','https://res.cloudinary.com/dgrmbspxb/image/upload/v1747485898/wkz3hdommeuav25jqxdf.png','Hiệp Hòa','company@gmail.com','1234567','https://res.cloudinary.com/dgrmbspxb/image/upload/v1748774100/qcgjxgf8ggwe1ymvfjn3.jpg','verified','123','company','Prep là một startup Edtech (công nghệ giáo dục) hiện đang phát triển nền tảng học tập tương tác trực tuyến để học viên luyện thi chứng chỉ ngoại ngữ hiệu quả trong khi tiết kiệm tối ưu về thời gian và chi phí. Học viên được xây dựng lộ trình cá nhân hoá sao cho phù hợp với khả năng và mục tiêu của mình nhất. Prep với sứ mệnh mang chất lượng giáo dục cao nhất tới các bạn học sinh, sinh viên ở khắp mọi miền tổ quốc với chi phí hợp lý nhất. Giúp học sinh chinh phục những kỳ thi chuẩn hóa một cách chắc chắn và dễ dàng nhất. Tối ưu công nghệ cá nhân hóa để mỗi người học như có một giáo viên kèm riêng.'),('COM0002','Nguyen Anh','https://res.cloudinary.com/dgrmbspxb/image/upload/v1748790162/k7hdowqiowwhxwrlvlwr.png','Cantho','company1@gmail.com','1234125','https://res.cloudinary.com/dgrmbspxb/image/upload/v1748790167/hgufdukgttpq5rj9wssb.jpg','unverified','123','company',NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
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
