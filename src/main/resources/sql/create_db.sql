/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2015-06-07 20:42:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'webboot', 'm');
INSERT INTO `user` VALUES ('2', 'guhanjie', 'm');
INSERT INTO `user` VALUES ('3', 'girl', 'f');
INSERT INTO `user` VALUES ('4', '帅哥', 'm');
INSERT INTO `user` VALUES ('5', '美女', 'f');
