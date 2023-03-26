/*
Navicat MySQL Data Transfer

Source Server         : 118.195.152.41
Source Server Version : 50650
Source Host           : 118.195.152.41:3306
Source Database       : manager

Target Server Type    : MYSQL
Target Server Version : 50650
File Encoding         : 65001

Date: 2022-08-22 22:51:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_address
-- ----------------------------
DROP TABLE IF EXISTS `base_address`;
CREATE TABLE `base_address` (
                                `id` int(64) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `address` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT ',
  `label` varchar(500) COLLATE utf8mb4_unicode_ci COMMENT ',
                                `chain` varchar(50) COLLATE utf8mb4_unicode_ci COMMENT ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT=';
