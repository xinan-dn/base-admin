/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 17/09/2019 15:41:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
                                     `series` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
                                     `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT ',
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'cookie,
                                     `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ',
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = 'persistent_loginsROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority`  (
                                  `authority_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `authority_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
                                  `authority_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `create_time` datetime NOT NULL COMMENT ',
                                  `update_time` datetime NOT NULL COMMENT ',
  `authority_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
                                  PRIMARY KEY (`authority_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('3fb1c570496d4c09ab99b8d31b06ccc', 'ROLE_USER', ', '2019-09-10 10:08:58', '2019-09-10 10:08:58', '');
INSERT INTO `sys_authority` VALUES ('3fb1c570496d4c09ab99b8d31b06xxx', 'ROLE_SA', ', '2019-09-10 10:08:58', '2019-09-10 10:08:58', '/sys/**,/logging');
INSERT INTO `sys_authority` VALUES ('3fb1c570496d4c09ab99b8d31b06zzz', 'ROLE_ADMIN', ', '2019-09-10 10:08:58', '2019-09-10 10:08:58', '/sys/**');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `menu_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `menu_parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sort_weight` int(2) NULL DEFAULT NULL COMMENT ',
  `create_time` datetime NOT NULL COMMENT ',
  `update_time` datetime NOT NULL COMMENT ',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('35cb950cebb04bb18bb1d8b742a02005', 'XXX, '/xxx', '', 1, '2019-09-11 18:05:21', '2019-09-11 18:05:21');
INSERT INTO `sys_menu` VALUES ('35cb950cebb04bb18bb1d8b742a02xaa', ', '/sys/sysAuthority/authority', '35cb950cebb04bb18bb1d8b742a02xxx', 1, '2019-09-10 10:08:58', '2019-09-10 10:08:58');
INSERT INTO `sys_menu` VALUES ('35cb950cebb04bb18bb1d8b742a02xcc', ', '/sys/sysMenu/menu', '35cb950cebb04bb18bb1d8b742a02xxx', 2, '2019-09-10 10:08:58', '2019-09-10 10:08:58');
INSERT INTO `sys_menu` VALUES ('35cb950cebb04bb18bb1d8b742a02xxx', ', '/sys', '', 0, '2019-09-10 10:08:58', '2019-09-10 10:08:58');
INSERT INTO `sys_menu` VALUES ('35cb950cebb04bb18bb1d8b742a02xzz', ', '/sys/sysUser/user', '35cb950cebb04bb18bb1d8b742a02xxx', 3, '2019-09-10 10:08:58', '2019-09-10 10:08:58');
INSERT INTO `sys_menu` VALUES ('74315e162f524a4d88aa931f02416f26', ', '/monitor', '35cb950cebb04bb18bb1d8b742a02xxx', 4, '2020-06-10 15:07:07', '2020-06-10 15:07:07');
INSERT INTO `sys_menu` VALUES ('78fb15a19e894777afbc239d574da423', 'yyy, '', '', 2, '2020-10-16 13:04:41', '2020-10-16 13:04:41');
INSERT INTO `sys_menu` VALUES ('914aa22c78af4327822061f3eada4067', ', '/logging', '35cb950cebb04bb18bb1d8b742a02xxx', 5, '2019-09-11 11:19:52', '2019-09-11 11:19:52');
INSERT INTO `sys_menu` VALUES ('bcf17dc0ce304f0ba02d64ce21ddb4f9', ', '/sys/sysSetting/setting', '35cb950cebb04bb18bb1d8b742a02xxx', 0, '2019-09-17 10:46:11', '2019-09-17 10:46:11');
INSERT INTO `sys_menu` VALUES ('d6d8b301316b43f2a9fb33caf1286376', ', '/aa', '35cb950cebb04bb18bb1d8b742a02xxx', 6, '2020-10-30 17:02:15', '2020-10-30 17:02:15');
-- ----------------------------
-- Table structure for sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `sys_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sys_logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sys_bottom_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sys_notice_text` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT ',
  `create_time` datetime NULL DEFAULT NULL COMMENT ',
  `update_time` datetime NULL DEFAULT NULL COMMENT ',
  `user_init_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sys_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sys_api_encrypt` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'APIY/N',
  `sys_open_api_limiter_encrypt` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OpenAPIY/N',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
INSERT INTO `sys_setting` VALUES ('1', 'Base Admin', 'https://avatar.gitee.com/uploads/0/5137900_huanzi-qch.png!avatar100?1562729811', '2019 - 2020  XXX, '<h1 style=\"white-space: normal; text-align: center;\"><span style=\"color: rgb(255, 0, 0);\">style=\"white-space: normal;\"><span style=\"color: rgb(255, 0, 0);\">1style=\"white-space: normal;\"><span style=\"color: rgb(255, 0, 0);\">2, '2019-09-17 10:15:38', '2019-09-17 10:15:40', '123456', 'rgba(54, 123, 183,  0.73)', 'Y', 'N');

-- ----------------------------
-- Table structure for sys_shortcut_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_shortcut_menu`;
CREATE TABLE `sys_shortcut_menu`  (
  `shortcut_menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `shortcut_menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `shortcut_menu_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `shortcut_menu_parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `sort_weight` int(2) NULL DEFAULT NULL COMMENT ',
  `create_time` datetime NOT NULL COMMENT ',
  `update_time` datetime NOT NULL COMMENT ',
  PRIMARY KEY (`shortcut_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_shortcut_menu
-- ----------------------------
INSERT INTO `sys_shortcut_menu` VALUES ('104370a3fa7948bab156afd4a5f2a730', ', '', '1', '', 0, '2019-09-12 18:35:13', '2019-09-12 18:35:13');
INSERT INTO `sys_shortcut_menu` VALUES ('72d94b41b9994038bd2f2135a1de28d8', ', '', 'b5ac62e154964151a19c565346bb354a', '', 0, '2019-09-17 14:36:28', '2019-09-17 14:36:28');
INSERT INTO `sys_shortcut_menu` VALUES ('88353f04ad5d47b182c984bfbb1693cc', 'ggg', '/xxx', 'b5ac62e154964151a19c565346bb354a', '72d94b41b9994038bd2f2135a1de28d8', 0, '2019-09-17 14:36:50', '2019-09-17 14:36:50');
INSERT INTO `sys_shortcut_menu` VALUES ('bd36d4507327434eb57b67b21343246f', ', 'https://cloud.tencent.com/', '1', '104370a3fa7948bab156afd4a5f2a730', 0, '2020-10-15 18:42:39', '2020-12-31 14:44:18');
INSERT INTO `sys_shortcut_menu` VALUES ('c309dafafe964a9d8de021b0da193bad', ', '/aaa', '1', 'db234c8f4fad4b0c9a4522e872c0f588', 0, '2020-10-16 10:20:20', '2020-10-16 10:20:20');
INSERT INTO `sys_shortcut_menu` VALUES ('cf78ced9ce7b480c85812540d1936145', ', 'https://www.baidu.com', '1', '104370a3fa7948bab156afd4a5f2a730', 2, '2019-09-12 18:35:39', '2020-12-31 14:44:27');
INSERT INTO `sys_shortcut_menu` VALUES ('cf78ced9ce7b480c85fd2540d1936145', ', 'https://www.aliyun.com/', '1', '104370a3fa7948bab156afd4a5f2a730', 1, '2019-09-12 18:35:39', '2020-12-31 14:44:23');
INSERT INTO `sys_shortcut_menu` VALUES ('db234c8f4fad4b0c9a4522e872c0f588', ', '', '1', '', 1, '2020-10-16 10:20:02', '2020-10-16 10:20:02');
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `valid` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `limited_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ',
  `expired_time` datetime NULL DEFAULT NULL COMMENT ',
  `last_change_pwd_time` datetime NOT NULL COMMENT ',
  `last_login_time` datetime NOT NULL COMMENT ',
  `limit_multi_login` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `create_time` datetime NOT NULL COMMENT ',
  `update_time` datetime NOT NULL COMMENT ',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'sa', ', 'E10ADC3949BA59ABBE56E057F20F883E', 'Y', '', NULL, '2019-09-17 12:00:36', '2019-09-17 12:00:36', 'Y', '2019-07-19 16:36:03', '2019-09-17 12:00:36');
INSERT INTO `sys_user` VALUES ('2', 'admin', ', 'E10ADC3949BA59ABBE56E057F20F883E', 'Y', '', NULL, '2019-09-17 12:00:36', '2019-09-17 12:00:36', 'N', '2019-07-19 16:36:03', '2019-09-12 16:14:28');
INSERT INTO `sys_user` VALUES ('3fb1c570496d4c09ab99b8d31b0671cf', 'daji', ', 'E10ADC3949BA59ABBE56E057F20F883E', 'Y', '', NULL, '2019-09-17 12:00:36', '2019-09-17 12:00:36', 'Y', '2019-09-11 18:11:41', '2019-09-17 12:09:47');
INSERT INTO `sys_user` VALUES ('b5ac62e154964151a19c565346bb354a', 'xiaofang', ', '96E79218965EB72C92A549DD5A330112', 'Y', '', NULL, '2019-09-17 12:00:36', '2019-09-17 12:00:36', 'N', '2019-09-17 14:12:41', '2019-09-17 14:28:57');

-- ----------------------------
-- Table structure for sys_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_authority`;
CREATE TABLE `sys_user_authority`  (
  `user_authority_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `authority_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `create_time` datetime NOT NULL COMMENT ',
  `update_time` datetime NOT NULL COMMENT ',
  PRIMARY KEY (`user_authority_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_authority
-- ----------------------------
INSERT INTO `sys_user_authority` VALUES ('0dc1b156ed544c0986823e9cd818da08', '2', '3fb1c570496d4c09ab99b8d31b06ccc', '2019-09-12 16:14:28', '2019-09-12 16:14:28');
INSERT INTO `sys_user_authority` VALUES ('90c18739f3ad41ae8010f6c2b7eeaac5', '3fb1c570496d4c09ab99b8d31b0671cf', '3fb1c570496d4c09ab99b8d31b06ccc', '2019-09-17 12:09:47', '2019-09-17 12:09:47');
INSERT INTO `sys_user_authority` VALUES ('9ca34956ceae4af0a74f4931344e9d1b', '1', '3fb1c570496d4c09ab99b8d31b06ccc', '2019-09-17 12:00:37', '2019-09-17 12:00:37');
INSERT INTO `sys_user_authority` VALUES ('a414567aaae54b42b8344da02795cb91', '2', '3fb1c570496d4c09ab99b8d31b06zzz', '2019-09-12 16:14:28', '2019-09-12 16:14:28');
INSERT INTO `sys_user_authority` VALUES ('b34f7092406c46189fee2690d9f6e493', '1', '3fb1c570496d4c09ab99b8d31b06xxx', '2019-09-17 12:00:37', '2019-09-17 12:00:37');
INSERT INTO `sys_user_authority` VALUES ('de60e5bbbacf4c739e44a60130d0f534', 'b5ac62e154964151a19c565346bb354a', '3fb1c570496d4c09ab99b8d31b06ccc', '2019-09-17 14:28:58', '2019-09-17 14:28:58');
INSERT INTO `sys_user_authority` VALUES ('f6514b57d1524afd8dfa7cb2c3ca6a11', '1', '3fb1c570496d4c09ab99b8d31b06zzz', '2019-09-17 12:00:37', '2019-09-17 12:00:37');

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu`  (
  `user_menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ',
  `create_time` datetime NOT NULL COMMENT ',
  `update_time` datetime NOT NULL COMMENT ',
  PRIMARY KEY (`user_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_menu
-- ----------------------------
INSERT INTO `sys_user_menu` VALUES ('1337996a0aba460bbf0b82db9a1da207', '1', '35cb950cebb04bb18bb1d8b742a02xxx', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('3232782f25ec44b09438ab9805b85f83', '2', '35cb950cebb04bb18bb1d8b742a02xcc', '2019-09-12 16:14:28', '2019-09-12 16:14:28');
INSERT INTO `sys_user_menu` VALUES ('39d6a157972e400fabcd753d3766efc9', '1', '35cb950cebb04bb18bb1d8b742a02xaa', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('3b0a160e8c624b2f86918afcd5107704', '1', '35cb950cebb04bb18bb1d8b742a02xcc', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('57791437b9774d8abf74562a49c55a1a', '2', '35cb950cebb04bb18bb1d8b742a02xxx', '2019-09-12 16:14:28', '2019-09-12 16:14:28');
INSERT INTO `sys_user_menu` VALUES ('6afadafdc36c426182853761bf68d870', '1', '74315e162f524a4d88aa931f02416f26', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('6e8fe2b9307a4855ba7d006dc17c97ae', '3fb1c570496d4c09ab99b8d31b0671cf', '35cb950cebb04bb18bb1d8b742a02005', '2019-09-17 12:09:47', '2019-09-17 12:09:47');
INSERT INTO `sys_user_menu` VALUES ('81f4999dde514e0ea43acfc70bfd35a8', '1', '914aa22c78af4327822061f3eada4067', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('9f8ccddc9fa84e0b9ff74128d20e9024', '2', '35cb950cebb04bb18bb1d8b742a02xaa', '2019-09-12 16:14:28', '2019-09-12 16:14:28');
INSERT INTO `sys_user_menu` VALUES ('c4220e4602fd4f2ca70da046466c6b45', '2', '35cb950cebb04bb18bb1d8b742a02xzz', '2019-09-12 16:14:28', '2019-09-12 16:14:28');
INSERT INTO `sys_user_menu` VALUES ('cdf8f786c658437ba77eb7d7fdd6b9cb', '1', '35cb950cebb04bb18bb1d8b742a02xzz', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('d646090ba4114c85b0a2fc2c9082a188', '1', 'bcf17dc0ce304f0ba02d64ce21ddb4f9', '2020-06-10 15:07:23', '2020-06-10 15:07:23');
INSERT INTO `sys_user_menu` VALUES ('d8bfa6eb34ef4946bb2cd1b9c0dbac0d', 'b5ac62e154964151a19c565346bb354a', '35cb950cebb04bb18bb1d8b742a02005', '2019-09-17 14:28:58', '2019-09-17 14:28:58');
