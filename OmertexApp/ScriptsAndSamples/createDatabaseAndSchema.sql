CREATE DATABASE `OmertexTask` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `OmertexTask`;
CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
CREATE TABLE `inquiry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `customer` varchar(255) CHARACTER SET latin1 NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 NOT NULL,
  `topic` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_daffc6f35b904fd9816b34ba721` (`topic`),
  CONSTRAINT `FK_daffc6f35b904fd9816b34ba721` FOREIGN KEY (`topic`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
CREATE TABLE `inquiry_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `value` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `inquiry_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fd17f08d977d4525ae97bab45a4` (`inquiry_id`),
  CONSTRAINT `FK_fd17f08d977d4525ae97bab45a4` FOREIGN KEY (`inquiry_id`) REFERENCES `inquiry` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
