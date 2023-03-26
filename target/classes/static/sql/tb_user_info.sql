/*
Navicat MySQL Data Transfer

Source Server         : 118.195.152.41
Source Server Version : 50650
Source Host           : 118.195.152.41:3306
Source Database       : manager

Target Server Type    : MYSQL
Target Server Version : 50650
File Encoding         : 65001

Date: 2022-08-22 22:43:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
                                `id` int(64) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `name` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT ',
  `parent` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT ',
                                `level` int(5) DEFAULT NULL COMMENT ',
  `fz_level` int(5) DEFAULT NULL COMMENT ',
                                `children_num` int(5) DEFAULT NULL COMMENT ',
  `money` varchar(50) DEFAULT NULL COMMENT ',
                                `total_money` varchar(50) DEFAULT NULL COMMENT ',
  `parent_long_id` text COLLATE utf8mb4_unicode_ci COMMENT ',
                                `remark` text DEFAULT NULL COMMENT ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT=';
