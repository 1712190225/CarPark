/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : carpark

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 09/11/2020 10:39:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `areaid` int(11) NOT NULL AUTO_INCREMENT,
  `price` float(10, 1) NULL DEFAULT NULL,
  `totalnum` int(11) NULL DEFAULT NULL,
  `leftnum` int(11) NULL DEFAULT NULL,
  `status` tinyint(10) NULL DEFAULT NULL,
  PRIMARY KEY (`areaid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES (1, 2.8, 200, 100, 0);

-- ----------------------------
-- Table structure for parkcar
-- ----------------------------
DROP TABLE IF EXISTS `parkcar`;
CREATE TABLE `parkcar`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isAuth` tinyint(1) NULL DEFAULT NULL,
  `entertime` datetime(0) NULL DEFAULT NULL,
  `outtime` datetime(0) NULL DEFAULT NULL,
  `parktime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parkcar
-- ----------------------------
INSERT INTO `parkcar` VALUES (1, '浙A88888', 1, '2020-11-04 10:00:20', '2020-11-05 10:00:28', '1天0小时1分钟');

-- ----------------------------
-- Table structure for parkcarauth
-- ----------------------------
DROP TABLE IF EXISTS `parkcarauth`;
CREATE TABLE `parkcarauth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parkcarauth
-- ----------------------------
INSERT INTO `parkcarauth` VALUES (1, '浙A88888', '2020-11-04 09:40:12');
INSERT INTO `parkcarauth` VALUES (2, '浙B52364', '2020-11-04 15:58:42');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `loginid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`loginid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'cmz', '123456');
INSERT INTO `user` VALUES (2, 'ryh', '123456');
INSERT INTO `user` VALUES (3, '1234', '1234');
INSERT INTO `user` VALUES (4, '12345', '12345');
INSERT INTO `user` VALUES (5, '1234567', '1234567');
INSERT INTO `user` VALUES (6, 'q123', 'q123');
INSERT INTO `user` VALUES (7, 'qw123', 'qw123');
INSERT INTO `user` VALUES (8, 'qwe123', 'qwe123');

SET FOREIGN_KEY_CHECKS = 1;
