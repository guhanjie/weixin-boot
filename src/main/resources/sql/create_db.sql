/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : zhmv

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-09-01 16:35:10
*/

/*
 * create database sql: 
 * CREATE DATABASE `zhmv` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci 
 */

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `amount` decimal(6,2) DEFAULT NULL COMMENT '订单金额',
  `tip` decimal(6,2) DEFAULT NULL COMMENT '小费',
  `from_id` int(10) unsigned DEFAULT NULL COMMENT '起始点ID',
  `waypoints_ids` varchar(120) DEFAULT NULL COMMENT '途径点ID（多个position_id以,分隔，有顺序关系，最多支持10个）',
  `to_id` int(10) unsigned DEFAULT NULL COMMENT '目的点ID',
  `distance` decimal(6,2) DEFAULT '0.00' COMMENT '运送距离（包括途径点）',
  `vehicle` smallint(6) DEFAULT NULL COMMENT '车型（1：小面车， 2：金杯车，3：全顺）',
  `status` smallint(6) DEFAULT '0' COMMENT '订单状态（位表示法，第1-2位：是否下单成功，第3-4位：是否开始送货，第5-6位：是否支付完成）',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '下单人ID',
  `contactor` varchar(20) DEFAULT NULL COMMENT '订单联系人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `remark` varchar(100) DEFAULT NULL COMMENT '订单备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `detail` varchar(100) DEFAULT NULL COMMENT '详细信息：几号楼几单元',
  `floor` smallint(6) DEFAULT NULL COMMENT '楼层（0表示电梯房，1以上表示多层）',
  `geo_lat` varchar(20) DEFAULT NULL COMMENT '地理纬度',
  `geo_lng` varchar(20) DEFAULT NULL COMMENT '地理经度',
  PRIMARY KEY (`id`),
  KEY `IDX_LNG_LAT` (`geo_lat`,`geo_lng`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `open_id` varchar(50) DEFAULT NULL,
  `unionid` varchar(50) DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `language` varchar(15) DEFAULT NULL,
  `city` varchar(15) DEFAULT NULL,
  `province` varchar(15) DEFAULT NULL,
  `country` varchar(15) DEFAULT NULL,
  `subscribe_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_OPEN_ID` (`open_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
