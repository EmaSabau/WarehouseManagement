CREATE DATABASE  IF NOT EXISTS `warehouse` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `warehouse`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: warehouse
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `warehouseorder`
--

DROP TABLE IF EXISTS `warehouseorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouseorder` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `ClientID` int DEFAULT NULL,
  `ProductID` int DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `OrderDate` date DEFAULT NULL,
  `TotalPrice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `ClientID` (`ClientID`),
  KEY `ProductID` (`ProductID`),
  CONSTRAINT `warehouseorder_ibfk_1` FOREIGN KEY (`ClientID`) REFERENCES `warehouseclient` (`ClientID`),
  CONSTRAINT `warehouseorder_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouseorder`
--

LOCK TABLES `warehouseorder` WRITE;
/*!40000 ALTER TABLE `warehouseorder` DISABLE KEYS */;
INSERT INTO `warehouseorder` VALUES (1,1,1,2,'2024-05-13',31.98),(2,2,2,1,'2024-05-13',29.99),(3,1,3,1,'2024-05-13',49.99);
/*!40000 ALTER TABLE `warehouseorder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-14  5:50:05
