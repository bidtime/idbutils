/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.x
Source Server Version : 50505
Source Host           : 192.168.1.x:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-05-05 10:34:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_duty
-- ----------------------------
DROP TABLE IF EXISTS `t_duty`;
CREATE TABLE `t_duty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_duty
-- ----------------------------

INSERT INTO `t_duty` (`id`, `code`, `name`) VALUES (1, 'D-1', '技术部');
INSERT INTO `t_duty` (`id`, `code`, `name`) VALUES (2, 'D-2', '人事部');
