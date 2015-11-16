/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : fensydb

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2014-06-28 12:27:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lovej_articles
-- ----------------------------
DROP TABLE IF EXISTS `lovej_articles`;
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
);

-- ----------------------------
-- Table structure for lovej_article_tags
-- ----------------------------
DROP TABLE IF EXISTS `lovej_article_tags`;
CREATE TABLE `lovej_article_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `articleId` (`articleId`),
  KEY `tagId` (`tagId`),
  CONSTRAINT `lovej_article_tags_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `lovej_articles` (`id`),
  CONSTRAINT `lovej_article_tags_ibfk_2` FOREIGN KEY (`tagId`) REFERENCES `lovej_tags` (`id`)
);

-- ----------------------------
-- Table structure for lovej_attaches
-- ----------------------------
DROP TABLE IF EXISTS `lovej_attaches`;
CREATE TABLE `lovej_attaches` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `download` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `articleId` (`articleId`),
  CONSTRAINT `lovej_attaches_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `lovej_articles` (`id`)
);

-- ----------------------------
-- Table structure for lovej_categories
-- ----------------------------
DROP TABLE IF EXISTS `lovej_categories`;
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
);

-- ----------------------------
-- Table structure for lovej_comments
-- ----------------------------
DROP TABLE IF EXISTS `lovej_comments`;
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
);

-- ----------------------------
-- Table structure for lovej_contacts
-- ----------------------------
DROP TABLE IF EXISTS `lovej_contacts`;
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
);

-- ----------------------------
-- Table structure for lovej_links
-- ----------------------------
DROP TABLE IF EXISTS `lovej_links`;
CREATE TABLE `lovej_links` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `site` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `trash` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for lovej_site_configs
-- ----------------------------
DROP TABLE IF EXISTS `lovej_site_configs`;
CREATE TABLE `lovej_site_configs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `about` varchar(255) NOT NULL,
  `contactDescription` longtext,
  `icp` varchar(50) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for lovej_tags
-- ----------------------------
DROP TABLE IF EXISTS `lovej_tags`;
CREATE TABLE `lovej_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for lovej_users
-- ----------------------------
DROP TABLE IF EXISTS `lovej_users`;
CREATE TABLE `lovej_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `frozen` tinyint(1) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);
