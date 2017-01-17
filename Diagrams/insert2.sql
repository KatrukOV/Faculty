CREATE DATABASE  IF NOT EXISTS `faculty` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `faculty`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: faculty
-- ------------------------------------------------------
-- Server version	5.6.25-log

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
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject_id` bigint(20) NOT NULL,
  `student_user_person_id` bigint(20) NOT NULL,
  `status` enum('DECLARED','CONFIRMED','REJECTED','DELETED') NOT NULL,
  `rating` enum('A','B','C','D','E','Fx','F') DEFAULT NULL,
  `feedback` text,
  PRIMARY KEY (`id`),
  KEY `fk_evaluation_student1_idx` (`student_user_person_id`),
  KEY `fk_evaluation_subject1` (`subject_id`),
  CONSTRAINT `fk_evaluation_student1` FOREIGN KEY (`student_user_person_id`) REFERENCES `student` (`user_person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluation_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `period`
--

DROP TABLE IF EXISTS `period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `period` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` enum('DISTRIBUTION','LEARNING') DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `period`
--

LOCK TABLES `period` WRITE;
/*!40000 ALTER TABLE `period` DISABLE KEYS */;
INSERT INTO `period` VALUES (11,'DISTRIBUTION','2017-01-16'),(12,'DISTRIBUTION','2017-01-16'),(13,'LEARNING','2017-01-16');
/*!40000 ALTER TABLE `period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(40) NOT NULL,
  `name` varchar(30) NOT NULL,
  `patronymic` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='person data';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (8,'Leonardo','Levon','Rikkov'),(9,'Voloshin','Ivan','Yakovlevich'),(10,'Paton','Fedor','Vasilevich'),(11,'Tolstou','Stepan','Feliksovich'),(12,'Dostoevskiy','Ivan','Vasilevich'),(13,'Smit','Nikola','Gitsoniv'),(14,'Motilip','Fedor','Vasilevich');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `user_person_id` bigint(20) NOT NULL,
  `form` enum('FULL_TAME','EXTRAMURAL','EVENING','DISTANCE') DEFAULT NULL,
  `contract` enum('STATE_ORDER','CONTRACT') DEFAULT NULL,
  PRIMARY KEY (`user_person_id`),
  CONSTRAINT `fk_student_user1` FOREIGN KEY (`user_person_id`) REFERENCES `user` (`person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (9,'FULL_TAME','STATE_ORDER'),(13,'EVENING','CONTRACT'),(14,'DISTANCE','CONTRACT');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `teacher_user_person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_subject_teacher1_idx` (`teacher_user_person_id`),
  CONSTRAINT `fk_subject_teacher1` FOREIGN KEY (`teacher_user_person_id`) REFERENCES `teacher` (`user_person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'Mathematic',11),(3,'Phisics',10);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `user_person_id` bigint(20) NOT NULL,
  `position` enum('PROFESSOR','ASSOCIATE_PROFESSOR','ASSISTANT') DEFAULT NULL,
  PRIMARY KEY (`user_person_id`),
  CONSTRAINT `fk_teacher_user1` FOREIGN KEY (`user_person_id`) REFERENCES `user` (`person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (10,'PROFESSOR'),(11,'PROFESSOR'),(12,'ASSOCIATE_PROFESSOR');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `person_id` bigint(20) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('STUDENT','TEACHER','ADMIN') DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_user_person_idx` (`person_id`),
  CONSTRAINT `fk_user_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (8,'admin','d033e22ae348aeb5660fc2140aec35850c4da997','ADMIN'),(9,'volo','8cb2237d0679ca88db6464eac60da96345513964','STUDENT'),(10,'pat','8cb2237d0679ca88db6464eac60da96345513964','TEACHER'),(11,'tosl','8cb2237d0679ca88db6464eac60da96345513964','TEACHER'),(12,'dost','8cb2237d0679ca88db6464eac60da96345513964','TEACHER'),(13,'smit','8cb2237d0679ca88db6464eac60da96345513964','STUDENT'),(14,'mot','8cb2237d0679ca88db6464eac60da96345513964','STUDENT');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'faculty'
--

--
-- Dumping routines for database 'faculty'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-16 17:54:21
