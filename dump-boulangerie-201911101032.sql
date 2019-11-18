-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: boulangerie
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.38-MariaDB

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
-- Table structure for table `composante`
--

DROP TABLE IF EXISTS `composante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `composante` (
  `composante_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `quantite_disponible` float NOT NULL,
  `prix_par_unite_de_mesure` float NOT NULL,
  `unite_de_mesure_id` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`composante_id`),
  KEY `composante_fk` (`unite_de_mesure_id`),
  CONSTRAINT `composante_fk` FOREIGN KEY (`unite_de_mesure_id`) REFERENCES `unite_de_mesure` (`unite_de_mesure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `composante`
--

LOCK TABLES `composante` WRITE;
/*!40000 ALTER TABLE `composante` DISABLE KEYS */;
INSERT INTO `composante` VALUES (1,'farine',21150,4.5,1),(2,'levure',5450,22,1),(3,'eau',6740,1.33,2),(4,'sel',7845,53,1),(5,'oeuf',1354,580,NULL),(6,'lait',98764,3,2),(7,'cacao',18965,63.6,1),(8,'fromage',6987,143.6,1),(9,'salade',14785,800,NULL),(10,'tomate',85296,5,1),(11,'oignon',9638,3.2,1),(12,'mayonnaise',8527,20.85,1),(13,'ketchup',7419,10.54,1),(14,'moutarde',7894,9.46,1);
/*!40000 ALTER TABLE `composante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enregistrement`
--

DROP TABLE IF EXISTS `enregistrement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enregistrement` (
  `enregistrement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_enregistrement` date NOT NULL,
  `produit_fini_id` tinyint(3) unsigned NOT NULL,
  `quantite` int(10) unsigned NOT NULL,
  PRIMARY KEY (`enregistrement_id`),
  KEY `enregistrement_fk` (`produit_fini_id`),
  CONSTRAINT `enregistrement_fk` FOREIGN KEY (`produit_fini_id`) REFERENCES `produit_fini` (`produit_fini_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enregistrement`
--

LOCK TABLES `enregistrement` WRITE;
/*!40000 ALTER TABLE `enregistrement` DISABLE KEYS */;
/*!40000 ALTER TABLE `enregistrement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fabrication`
--

DROP TABLE IF EXISTS `fabrication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fabrication` (
  `produit_fini_id` tinyint(3) unsigned NOT NULL,
  `date_fabrication` date NOT NULL,
  `quantite` float NOT NULL,
  KEY `fabrication_fk` (`produit_fini_id`),
  CONSTRAINT `fabrication_fk` FOREIGN KEY (`produit_fini_id`) REFERENCES `produit_fini` (`produit_fini_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabrication`
--

LOCK TABLES `fabrication` WRITE;
/*!40000 ALTER TABLE `fabrication` DISABLE KEYS */;
INSERT INTO `fabrication` VALUES (1,'2019-11-01',10);
/*!40000 ALTER TABLE `fabrication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machine` (
  `machine_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `cout_horaire` float NOT NULL,
  PRIMARY KEY (`machine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
INSERT INTO `machine` VALUES (1,'Four',200),(2,'Mélangeur',75),(3,'Tamis à farine',140.75),(4,'Clayette',55.1);
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `machines_par_produit`
--

DROP TABLE IF EXISTS `machines_par_produit`;
/*!50001 DROP VIEW IF EXISTS `machines_par_produit`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `machines_par_produit` (
  `machine_nom` tinyint NOT NULL,
  `machine_id` tinyint NOT NULL,
  `machine_cout_horaire` tinyint NOT NULL,
  `produit_fini_id` tinyint NOT NULL,
  `produit_fini_nom` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `produit_fini`
--

DROP TABLE IF EXISTS `produit_fini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produit_fini` (
  `produit_fini_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `temps_de_fabrication` float NOT NULL,
  PRIMARY KEY (`produit_fini_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_fini`
--

LOCK TABLES `produit_fini` WRITE;
/*!40000 ALTER TABLE `produit_fini` DISABLE KEYS */;
INSERT INTO `produit_fini` VALUES (1,'Pain',4.41),(2,'Chocolat',8),(3,'Sandwich',5);
/*!40000 ALTER TABLE `produit_fini` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit_fini_composante`
--

DROP TABLE IF EXISTS `produit_fini_composante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produit_fini_composante` (
  `produit_fini_id` tinyint(3) unsigned NOT NULL,
  `composante_id` int(10) unsigned NOT NULL,
  `quantite_unitaire` float DEFAULT NULL,
  KEY `produit_fini_composante_fk_1` (`produit_fini_id`),
  KEY `produit_fini_composante_fk` (`composante_id`),
  CONSTRAINT `produit_fini_composante_fk` FOREIGN KEY (`composante_id`) REFERENCES `composante` (`composante_id`),
  CONSTRAINT `produit_fini_composante_fk_1` FOREIGN KEY (`produit_fini_id`) REFERENCES `produit_fini` (`produit_fini_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_fini_composante`
--

LOCK TABLES `produit_fini_composante` WRITE;
/*!40000 ALTER TABLE `produit_fini_composante` DISABLE KEYS */;
INSERT INTO `produit_fini_composante` VALUES (1,1,500),(1,4,5),(1,2,20),(1,3,300),(3,1,1200),(3,4,7),(3,2,35),(3,3,215),(3,5,1),(3,8,100),(3,9,0.25),(3,10,50),(3,11,50),(3,12,10),(2,3,1542),(2,5,4),(2,6,2224),(2,7,3579);
/*!40000 ALTER TABLE `produit_fini_composante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit_fini_machine`
--

DROP TABLE IF EXISTS `produit_fini_machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produit_fini_machine` (
  `produit_fini_id` tinyint(3) unsigned NOT NULL,
  `machine_id` tinyint(3) unsigned NOT NULL,
  KEY `produit_fini_machine_fk` (`produit_fini_id`),
  KEY `produit_fini_machine_fk_1` (`machine_id`),
  CONSTRAINT `produit_fini_machine_fk` FOREIGN KEY (`produit_fini_id`) REFERENCES `produit_fini` (`produit_fini_id`),
  CONSTRAINT `produit_fini_machine_fk_1` FOREIGN KEY (`machine_id`) REFERENCES `machine` (`machine_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_fini_machine`
--

LOCK TABLES `produit_fini_machine` WRITE;
/*!40000 ALTER TABLE `produit_fini_machine` DISABLE KEYS */;
INSERT INTO `produit_fini_machine` VALUES (1,1),(1,2),(1,3),(1,4),(2,2);
/*!40000 ALTER TABLE `produit_fini_machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `recette`
--

DROP TABLE IF EXISTS `recette`;
/*!50001 DROP VIEW IF EXISTS `recette`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `recette` (
  `produit_fini` tinyint NOT NULL,
  `composante` tinyint NOT NULL,
  `quantite_disponible` tinyint NOT NULL,
  `prix_par_unite_de_mesure` tinyint NOT NULL,
  `quantite_unitaire` tinyint NOT NULL,
  `unite_de_mesure` tinyint NOT NULL,
  `abreviation_unite_de_mesure` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `unite_de_mesure`
--

DROP TABLE IF EXISTS `unite_de_mesure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unite_de_mesure` (
  `unite_de_mesure_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `abreviation` varchar(100) NOT NULL,
  PRIMARY KEY (`unite_de_mesure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unite_de_mesure`
--

LOCK TABLES `unite_de_mesure` WRITE;
/*!40000 ALTER TABLE `unite_de_mesure` DISABLE KEYS */;
INSERT INTO `unite_de_mesure` VALUES (1,'gramme','g'),(2,'millilitre','ml');
/*!40000 ALTER TABLE `unite_de_mesure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'boulangerie'
--

--
-- Final view structure for view `machines_par_produit`
--

/*!50001 DROP TABLE IF EXISTS `machines_par_produit`*/;
/*!50001 DROP VIEW IF EXISTS `machines_par_produit`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `machines_par_produit` AS select `machine`.`nom` AS `machine_nom`,`machine`.`machine_id` AS `machine_id`,`machine`.`cout_horaire` AS `machine_cout_horaire`,`produit_fini`.`produit_fini_id` AS `produit_fini_id`,`produit_fini`.`nom` AS `produit_fini_nom` from ((`produit_fini_machine` join `produit_fini` on((`produit_fini_machine`.`produit_fini_id` = `produit_fini`.`produit_fini_id`))) join `machine` on((`produit_fini_machine`.`machine_id` = `machine`.`machine_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `recette`
--

/*!50001 DROP TABLE IF EXISTS `recette`*/;
/*!50001 DROP VIEW IF EXISTS `recette`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `recette` AS select `produit_fini`.`nom` AS `produit_fini`,`composante`.`nom` AS `composante`,`composante`.`quantite_disponible` AS `quantite_disponible`,`composante`.`prix_par_unite_de_mesure` AS `prix_par_unite_de_mesure`,`produit_fini_composante`.`quantite_unitaire` AS `quantite_unitaire`,`unite_de_mesure`.`nom` AS `unite_de_mesure`,`unite_de_mesure`.`abreviation` AS `abreviation_unite_de_mesure` from (((`composante` join `produit_fini_composante` on((`produit_fini_composante`.`composante_id` = `composante`.`composante_id`))) join `produit_fini` on((`produit_fini_composante`.`produit_fini_id` = `produit_fini`.`produit_fini_id`))) join `unite_de_mesure` on((`unite_de_mesure`.`unite_de_mesure_id` = `composante`.`unite_de_mesure_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-10 10:32:43
