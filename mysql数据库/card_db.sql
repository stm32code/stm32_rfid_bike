/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : card_db

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2024-04-05 21:52:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_accessinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_accessinfo`;
CREATE TABLE `t_accessinfo` (
  `accessId` int(11) NOT NULL AUTO_INCREMENT COMMENT '进出记录id',
  `carObj` varchar(50) NOT NULL COMMENT '车牌号',
  `rfid` varchar(20) NOT NULL COMMENT '所属rfid',
  `userId` varchar(30) NOT NULL COMMENT '所属用户',
  `inTime` varchar(20) DEFAULT NULL COMMENT '进入时间',
  `outTime` varchar(20) DEFAULT NULL COMMENT '出去时间',
  `memo` varchar(600) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`accessId`),
  KEY `carObj` (`carObj`),
  KEY `userId` (`userId`),
  CONSTRAINT `t_accessinfo_ibfk_1` FOREIGN KEY (`carObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `t_accessinfo_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_accessinfo
-- ----------------------------
INSERT INTO `t_accessinfo` VALUES ('1', '111', '11', 'user', '2024-04-03 01:20:05', '2024-04-03 01:20:07', '11');
INSERT INTO `t_accessinfo` VALUES ('2', '川A-503', '222', 'user2', '2024-04-05 15:54:06', '2024-04-06 13:17:38', '来了');
INSERT INTO `t_accessinfo` VALUES ('3', '川A-503', 'E3575D1A', 'user2', '2024-04-05 20:45:32', '2024-04-05 20:46:00', '发发');

-- ----------------------------
-- Table structure for `t_jiedong`
-- ----------------------------
DROP TABLE IF EXISTS `t_jiedong`;
CREATE TABLE `t_jiedong` (
  `jieDongId` int(11) NOT NULL AUTO_INCREMENT COMMENT '解冻id',
  `proObj` varchar(50) NOT NULL COMMENT '车牌号',
  `userObj` varchar(30) NOT NULL COMMENT '解冻用户',
  `jiedongTime` varchar(20) DEFAULT NULL COMMENT '解冻申请时间',
  `reason` varchar(20) DEFAULT NULL COMMENT '原因',
  `state` varchar(20) NOT NULL COMMENT '处理状态',
  `result` varchar(600) DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (`jieDongId`),
  KEY `proObj` (`proObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_jiedong_ibfk_1` FOREIGN KEY (`proObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `t_jiedong_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_jiedong
-- ----------------------------
INSERT INTO `t_jiedong` VALUES ('1', '111', 'user', '2024-04-03 01:20:28', '11', '11', '11');
INSERT INTO `t_jiedong` VALUES ('2', '川A-503', 'user2', '2024-04-05 18:49:49', '找到了', '审核通过', 'fafaf s');

-- ----------------------------
-- Table structure for `t_lostapply`
-- ----------------------------
DROP TABLE IF EXISTS `t_lostapply`;
CREATE TABLE `t_lostapply` (
  `lostApplyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '挂失申请id',
  `proObj` varchar(50) NOT NULL COMMENT '车牌号',
  `lostUserObj` varchar(30) NOT NULL COMMENT '挂失用户',
  `lostTime` varchar(20) DEFAULT NULL COMMENT '挂失时间',
  `reason` varchar(20) DEFAULT NULL COMMENT '挂失原因',
  `applyState` varchar(20) NOT NULL COMMENT '处理状态',
  `result` varchar(600) DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (`lostApplyId`),
  KEY `proObj` (`proObj`),
  KEY `lostUserObj` (`lostUserObj`),
  CONSTRAINT `t_lostapply_ibfk_1` FOREIGN KEY (`proObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `t_lostapply_ibfk_2` FOREIGN KEY (`lostUserObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lostapply
-- ----------------------------
INSERT INTO `t_lostapply` VALUES ('1', '111', 'user', '2024-04-03 01:20:17', '11', '1', '11');
INSERT INTO `t_lostapply` VALUES ('2', '川A-503', 'user2', '2024-04-05 18:24:27', '掉了', '审核通过', 'Ok');

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `productNo` varchar(50) NOT NULL COMMENT 'productNo',
  `productClassObj` int(11) NOT NULL COMMENT '产品类别',
  `productName` varchar(50) NOT NULL COMMENT '产品名称',
  `mainPhoto` varchar(60) NOT NULL COMMENT '产品主图',
  `price` float NOT NULL COMMENT '产品价格',
  `productDesc` varchar(5000) NOT NULL COMMENT '产品描述',
  `addTime` varchar(20) DEFAULT NULL COMMENT '发布时间',
  `userObj` varchar(30) NOT NULL COMMENT '所属用户',
  `proState` varchar(20) NOT NULL COMMENT '车辆状态',
  `rfid` varchar(50) DEFAULT NULL COMMENT '电动车RFID',
  PRIMARY KEY (`productNo`),
  KEY `productClassObj` (`productClassObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_product_ibfk_1` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`classId`),
  CONSTRAINT `t_product_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('111', '1', '111', 'upload/NoImage.jpg', '111', '<p>11<br/></p>', '2024-04-03 01:19:43', 'user', '待审核', '111');
INSERT INTO `t_product` VALUES ('川A-503', '1', '雅迪电动车', 'upload/e0c95a57-582e-4828-9163-dc45c6ed4c58.jpg', '1300', '<p>发发发发法法师affaf</p>', '2024-04-05 12:11:14', 'user2', '正常', 'E3575D1A');

-- ----------------------------
-- Table structure for `t_productclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_productclass`;
CREATE TABLE `t_productclass` (
  `classId` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `className` varchar(40) NOT NULL COMMENT '类别名称',
  `classDesc` varchar(500) NOT NULL COMMENT '类别描述',
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productclass
-- ----------------------------
INSERT INTO `t_productclass` VALUES ('1', '11', '111');

-- ----------------------------
-- Table structure for `t_reissue`
-- ----------------------------
DROP TABLE IF EXISTS `t_reissue`;
CREATE TABLE `t_reissue` (
  `reissueId` int(11) NOT NULL AUTO_INCREMENT COMMENT '补办id',
  `proObj` varchar(50) NOT NULL COMMENT '车牌号',
  `userObj` varchar(30) NOT NULL COMMENT '补办用户',
  `reissueTime` varchar(20) DEFAULT NULL COMMENT '补办时间',
  `ememo` varchar(600) DEFAULT NULL COMMENT '补办原因',
  `state` varchar(20) NOT NULL COMMENT '处理状态',
  `rfid` varchar(50) NOT NULL COMMENT 'rfid',
  PRIMARY KEY (`reissueId`),
  KEY `proObj` (`proObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_reissue_ibfk_1` FOREIGN KEY (`proObj`) REFERENCES `t_product` (`productNo`),
  CONSTRAINT `t_reissue_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reissue
-- ----------------------------
INSERT INTO `t_reissue` VALUES ('1', '111', 'user', '2024-04-03 01:20:38', '11', '11', '11');
INSERT INTO `t_reissue` VALUES ('2', '川A-503', 'user2', '2024-04-05 19:34:20', '卡烂了', '审核通过', '444');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) DEFAULT NULL COMMENT '家庭地址',
  `schoolState` varchar(20) NOT NULL COMMENT '是否在校',
  `roleName` varchar(20) NOT NULL COMMENT '用户身份',
  `regTime` varchar(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user', '123', 'a', 'b', '2024-04-03', 'upload/NoImage.jpg', '2', '3', 'fd', '是的', '学生', '2024-04-03 01:19:01');
INSERT INTO `t_userinfo` VALUES ('user2', '123', 'bb', '男', '2024-04-11', 'upload/1166094d-fbbe-41b7-a850-e9046d8c55ee.jpg', '2333', '4444', '6555', '否', '老师', '2024-04-05 11:32:29');
