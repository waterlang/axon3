/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.50-MariaDB-wsrep : Database - axon
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`axon` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `axon`;

/*Table structure for table `TokenEntry`  这个是JPA的建表语句*/

DROP TABLE IF EXISTS `TokenEntry`;

CREATE TABLE `TokenEntry` (
  `processorName` varchar(50) NOT NULL,
  `segment` int(11) NOT NULL,
  `token` text,
  `tokenType` varchar(600) DEFAULT NULL,
  `timestamp` varchar(100) DEFAULT NULL,
  `owner` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`processorName`,`segment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `token_entry` 这个是jdbc的建表语句 */

DROP TABLE IF EXISTS `token_entry`;

CREATE TABLE `token_entry` (
  `processor_name` varchar(100) NOT NULL,
  `segment` int(10) NOT NULL,
  `token` varchar(500) DEFAULT NULL,
  `token_type` varchar(100) DEFAULT NULL,
  `timestamp` varchar(50) DEFAULT NULL,
  `owner` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`processor_name`,`segment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
