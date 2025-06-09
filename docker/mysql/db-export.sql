-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: skilllink
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `ad`
--

DROP TABLE IF EXISTS `ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `rate` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtqlc24ixmsomj3k2gpq6kivd8` (`provider_id`),
  CONSTRAINT `FKtqlc24ixmsomj3k2gpq6kivd8` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad`
--

LOCK TABLES `ad` WRITE;
/*!40000 ALTER TABLE `ad` DISABLE KEYS */;
/*!40000 ALTER TABLE `ad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ads`
--

DROP TABLE IF EXISTS `ads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ads` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `rate` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl6b6h8cl3sg5rnoyuxeiogdtd` (`provider_id`),
  CONSTRAINT `FKl6b6h8cl3sg5rnoyuxeiogdtd` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ads`
--

LOCK TABLES `ads` WRITE;
/*!40000 ALTER TABLE `ads` DISABLE KEYS */;
/*!40000 ALTER TABLE `ads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('client','ROLE_CLIENT'),('malina','ROLE_CLIENT'),('prov','ROLE_PROVIDER'),('provider','ROLE_PROVIDER'),('radu','ROLE_PROVIDER'),('test_p','ROLE_PROVIDER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `availability_slot`
--

DROP TABLE IF EXISTS `availability_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `availability_slot` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `date` date NOT NULL,
  `end_time` time(6) NOT NULL,
  `start_time` time(6) NOT NULL,
  `calendar_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmtw93c76emhw50wkthtok54jg` (`calendar_id`),
  CONSTRAINT `FKmtw93c76emhw50wkthtok54jg` FOREIGN KEY (`calendar_id`) REFERENCES `calendars` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availability_slot`
--

LOCK TABLES `availability_slot` WRITE;
/*!40000 ALTER TABLE `availability_slot` DISABLE KEYS */;
INSERT INTO `availability_slot` VALUES (_binary '|m–\è~\×N<º÷¬ \'\Ñg','2025-05-17 17:16:09.582766','2025-05-16','00:21:00.000000','12:16:00.000000',_binary '\Ô\åx&nGÉ¡—_a<ž”'),(_binary 'Œò\Å\×l\æLºjI\Ú\Î´\Ø','2025-05-17 17:16:30.771463','2025-05-31','20:21:00.000000','20:21:00.000000',_binary '\Ô\åx&nGÉ¡—_a<ž”');
/*!40000 ALTER TABLE `availability_slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `connection_link` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_time` time(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `rate_percent` double DEFAULT NULL,
  `start_time` time(6) DEFAULT NULL,
  `booking_type` tinyint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `client_id` binary(16) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp212rkyrbbp4r4wphugqoxwhs` (`client_id`),
  KEY `FK8sfxbohqqrpnfdye37k3ptuaq` (`provider_id`),
  CONSTRAINT `FK8sfxbohqqrpnfdye37k3ptuaq` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`),
  CONSTRAINT `FKp212rkyrbbp4r4wphugqoxwhs` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `booking_chk_1` CHECK ((`booking_type` between 0 and 1)),
  CONSTRAINT `booking_chk_2` CHECK ((`status` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendars`
--

DROP TABLE IF EXISTS `calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendars` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK1hw299l81nudlfvtfi3vsct8a` (`provider_id`),
  CONSTRAINT `FK1qfijeg90u84shhg8y3vconmg` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendars`
--

LOCK TABLES `calendars` WRITE;
/*!40000 ALTER TABLE `calendars` DISABLE KEYS */;
INSERT INTO `calendars` VALUES (_binary '\Ô\åx&nGÉ¡—_a<ž”','2025-05-17 17:15:29.396820',_binary '6\î¯\å`E¹³ty’Q\"\É');
/*!40000 ALTER TABLE `calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (_binary '£8;O2«ø\Ï%ˆS„','2025-05-17 19:59:46.525964','malina123','malina213','malina','malina.jfif'),(_binary 'œWûþ\Û\éB]»a\Ïa[?žŠ','2025-05-11 13:58:44.015657','client123','client123',NULL,NULL);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_providers`
--

DROP TABLE IF EXISTS `clients_providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_providers` (
  `client_id` binary(16) NOT NULL,
  `provider_id` binary(16) NOT NULL,
  PRIMARY KEY (`client_id`,`provider_id`),
  KEY `FKmq920gte3h9yef5ksksqhcua2` (`provider_id`),
  CONSTRAINT `FK17l42drwkfhr7pbabu11dxlhy` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `FKmq920gte3h9yef5ksksqhcua2` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_providers`
--

LOCK TABLES `clients_providers` WRITE;
/*!40000 ALTER TABLE `clients_providers` DISABLE KEYS */;
/*!40000 ALTER TABLE `clients_providers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `amount` decimal(12,2) NOT NULL,
  `date_created` date DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `client_id` binary(16) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ioqm804urbgy986pdtwqtl0x` (`client_id`),
  KEY `FKaejgk4uta7vrjvjwgyolgyv8i` (`provider_id`),
  CONSTRAINT `FK9ioqm804urbgy986pdtwqtl0x` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `FKaejgk4uta7vrjvjwgyolgyv8i` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providers`
--

DROP TABLE IF EXISTS `providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `providers` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `rate` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providers`
--

LOCK TABLES `providers` WRITE;
/*!40000 ALTER TABLE `providers` DISABLE KEYS */;
INSERT INTO `providers` VALUES (_binary '6\î¯\å`E¹³ty’Q\"\É','2025-05-17 20:39:35.129822','prov123','prov','prov','prov.png',0),(_binary '\ßÿ£úGÙ½\Ê\Ð—Y\ï¼','2025-05-11 13:58:25.219655','provider123','provider123',NULL,NULL,0),(_binary '?l·¹[\ËCS–õ\Ë\ÇÇ‘\Ô','2025-05-17 20:19:37.280135','test_p','test_p','test_p',NULL,0),(_binary 'ˆŸ\ÄÙ¤óJ•·I46‰vñ','2025-05-17 23:09:59.722772','radu123','radu123','radu','radu.png',0);
/*!40000 ALTER TABLE `providers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `stars` int DEFAULT NULL,
  `client_id` binary(16) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo2cmyvyjrvumg4b3de9dcvfxa` (`client_id`),
  KEY `FKrsomnwai2734u8lqdqu5yd96x` (`provider_id`),
  CONSTRAINT `FKo2cmyvyjrvumg4b3de9dcvfxa` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `FKrsomnwai2734u8lqdqu5yd96x` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slot` (
  `id` binary(16) NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `approved` bit(1) NOT NULL,
  `slot_type` tinyint DEFAULT NULL,
  `client_id` binary(16) DEFAULT NULL,
  `provider_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41r7mdjxup3os37o6f8y43aos` (`client_id`),
  KEY `FK853tec4mu3xii65v23xbnnd7g` (`provider_id`),
  CONSTRAINT `FK41r7mdjxup3os37o6f8y43aos` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `FK853tec4mu3xii65v23xbnnd7g` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`),
  CONSTRAINT `slot_chk_1` CHECK ((`slot_type` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot`
--

LOCK TABLES `slot` WRITE;
/*!40000 ALTER TABLE `slot` DISABLE KEYS */;
/*!40000 ALTER TABLE `slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('client','$2a$10$Hq0f0lM0DUbqsCB7lI5kI.xofWO2Hv9.sdR7/zZbKoTeVSRWQRQmO',1),('malina','$2a$10$xYiSAzJcnE6Btxsx6FCEu.y2xL03X4PQOGHbTMK.Ky8j6TwBWonn.',1),('prov','$2a$10$UuO1wgd4i2E9/HYVA8SwTeevLU.FxIqS4Ba63kr8LmJHiRIkq58Re',1),('provider','$2a$10$NPbgEEhQ5xwzGWHJUXyhMODB6Z8yj8wZteAd8EbaWddfwuTeABXta',1),('radu','$2a$10$zl96ooBVwpL4BEhkcqBf3eYeqBPcpG1VCHww9rXzrTQUoxgtF7k6G',1),('test_p','$2a$10$izMJo2S3PDKuNodhMMzjPul8aNVDi0RthmCKvfiC5h8OsR6/H.t5W',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-09 18:59:14
