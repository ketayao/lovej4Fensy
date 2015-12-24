-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: localhost    Database: fensydb
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `lovej_article_tags`
--

DROP TABLE IF EXISTS `lovej_article_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_article_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `articleId` (`articleId`),
  KEY `tagId` (`tagId`),
  CONSTRAINT `lovej_article_tags_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `lovej_articles` (`id`),
  CONSTRAINT `lovej_article_tags_ibfk_2` FOREIGN KEY (`tagId`) REFERENCES `lovej_tags` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_articles`
--

DROP TABLE IF EXISTS `lovej_articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_articles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `modifyTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `postTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `quote` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `summary` text,
  `title` varchar(255) NOT NULL,
  `view` int(11) NOT NULL,
  `categoryId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `trash` tinyint(1) DEFAULT NULL,
  `topTime` timestamp NULL DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `permalink` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `categoryId` (`categoryId`),
  CONSTRAINT `lovej_articles_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `lovej_users` (`id`),
  CONSTRAINT `lovej_articles_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `lovej_categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_attaches`
--

DROP TABLE IF EXISTS `lovej_attaches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_attaches` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `download` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `articleId` (`articleId`),
  CONSTRAINT `lovej_attaches_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `lovej_articles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_categories`
--

DROP TABLE IF EXISTS `lovej_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `priority` int(11) NOT NULL,
  `trash` tinyint(1) NOT NULL,
  `type` varchar(10) NOT NULL,
  `parentId` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `lovej_categories_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `lovej_categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_comments`
--

DROP TABLE IF EXISTS `lovej_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `postIP` varchar(20) NOT NULL,
  `postTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `site` varchar(255) DEFAULT NULL,
  `articleId` bigint(20) NOT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `articleId` (`articleId`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `lovej_comments_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `lovej_articles` (`id`),
  CONSTRAINT `lovej_comments_ibfk_2` FOREIGN KEY (`parentId`) REFERENCES `lovej_comments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=625 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_contacts`
--

DROP TABLE IF EXISTS `lovej_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_contacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `postIP` varchar(20) NOT NULL,
  `postTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `site` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `trash` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_links`
--

DROP TABLE IF EXISTS `lovej_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_links` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `site` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `trash` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_site_configs`
--

DROP TABLE IF EXISTS `lovej_site_configs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_site_configs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `about` varchar(255) NOT NULL,
  `contactDescription` longtext,
  `icp` varchar(50) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_tags`
--

DROP TABLE IF EXISTS `lovej_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lovej_users`
--

DROP TABLE IF EXISTS `lovej_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lovej_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `frozen` tinyint(1) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  `forgetCode` varchar(45) DEFAULT NULL,
  `forgetDuration` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-24 18:03:43
