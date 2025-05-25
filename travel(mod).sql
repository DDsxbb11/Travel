/*
 Navicat Premium Data Transfer

 Source Server         : Dunbobo
 Source Server Type    : MySQL
 Source Server Version : 50723 (5.7.23-log)
 Source Host           : localhost:3306
 Source Schema         : travel

 Target Server Type    : MySQL
 Target Server Version : 50723 (5.7.23-log)
 File Encoding         : 65001

 Date: 25/05/2025 10:28:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attraction_img
-- ----------------------------
DROP TABLE IF EXISTS `attraction_img`;
CREATE TABLE `attraction_img`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片名称',
  `attraction_id` tinyint(4) NOT NULL COMMENT '图片所属对象id',
  `type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '图片类型',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for attraction_info
-- ----------------------------
DROP TABLE IF EXISTS `attraction_info`;
CREATE TABLE `attraction_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `theme` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题',
  `province` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份id',
  `city` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '国家id',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `open_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开放时间',
  `is_fee` tinyint(4) NOT NULL COMMENT '是否免费',
  `children_price` decimal(10, 2) NOT NULL COMMENT '儿童门票价格',
  `adult_price` decimal(10, 2) NOT NULL COMMENT '成人门票价格',
  `longitude` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经度',
  `latitude` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '纬度',
  `rating` decimal(2, 1) NULL DEFAULT NULL COMMENT '评分',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '景点描述',
  `traffic_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交通描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '景点信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for attraction_order_info
-- ----------------------------
DROP TABLE IF EXISTS `attraction_order_info`;
CREATE TABLE `attraction_order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attraction_id` bigint(20) NOT NULL COMMENT '景点id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `children_number` int(11) NOT NULL DEFAULT 0 COMMENT '儿童人数',
  `adult_number` int(11) NOT NULL DEFAULT 0 COMMENT '成人人数',
  `total_price` decimal(10, 2) NOT NULL COMMENT '总价格',
  `appointment_time` date NOT NULL COMMENT '下单时间',
  `payment_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付类型',
  `state` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0：未使用，1：已使用，2已过期）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '景点订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for attraction_theme
-- ----------------------------
DROP TABLE IF EXISTS `attraction_theme`;
CREATE TABLE `attraction_theme`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '主题' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for browsing_history_info
-- ----------------------------
DROP TABLE IF EXISTS `browsing_history_info`;
CREATE TABLE `browsing_history_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '实体id',
  `item_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体类型',
  `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体名称',
  `item_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片url',
  `item_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体描述',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for city_info
-- ----------------------------
DROP TABLE IF EXISTS `city_info`;
CREATE TABLE `city_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '城市id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市名称',
  `province_id` int(11) NULL DEFAULT NULL COMMENT '所属省份id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6602 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for collections_info
-- ----------------------------
DROP TABLE IF EXISTS `collections_info`;
CREATE TABLE `collections_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '实体id',
  `item_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体类型',
  `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体名称',
  `item_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片url',
  `item_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体描述',
  `collections_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `item_id` bigint(20) NOT NULL COMMENT '实体id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '交通描述',
  `rating` decimal(2, 1) NOT NULL COMMENT '评分',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for comment_img
-- ----------------------------
DROP TABLE IF EXISTS `comment_img`;
CREATE TABLE `comment_img`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` bigint(20) NOT NULL COMMENT '用户id',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for district_info
-- ----------------------------
DROP TABLE IF EXISTS `district_info`;
CREATE TABLE `district_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `city_id` int(11) NULL DEFAULT NULL COMMENT '所属城市id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 695345 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for facility_info
-- ----------------------------
DROP TABLE IF EXISTS `facility_info`;
CREATE TABLE `facility_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签类型',
  `svg_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'svg路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设施信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for hotel_info
-- ----------------------------
DROP TABLE IF EXISTS `hotel_info`;
CREATE TABLE `hotel_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片',
  `level` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '等级',
  `province` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份',
  `city` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市',
  `district` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `longitude` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经度',
  `latitude` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '纬度',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '酒店描述',
  `rating` decimal(4, 1) NOT NULL DEFAULT 0.0 COMMENT '评分',
  `open_time` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开业时间',
  `room_number` int(11) NOT NULL COMMENT '房间数',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  `traffic_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交通描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '酒店信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for hotel_order_info
-- ----------------------------
DROP TABLE IF EXISTS `hotel_order_info`;
CREATE TABLE `hotel_order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店id',
  `room_id` bigint(20) NOT NULL COMMENT '房间id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `number` int(11) NOT NULL COMMENT '数量',
  `price` decimal(10, 2) NOT NULL COMMENT '总价格',
  `payment_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付方式',
  `start_time` date NOT NULL COMMENT '入住时间',
  `end_time` date NOT NULL COMMENT '离开时间',
  `state` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态：0未使用，1：已使用，2：已过期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房间订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for item_facility
-- ----------------------------
DROP TABLE IF EXISTS `item_facility`;
CREATE TABLE `item_facility`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `item_id` bigint(20) NOT NULL COMMENT '实体id',
  `facility_id` bigint(20) NOT NULL COMMENT '设施id',
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实体设施对照表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lable_info
-- ----------------------------
DROP TABLE IF EXISTS `lable_info`;
CREATE TABLE `lable_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '攻略标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for policy_info
-- ----------------------------
DROP TABLE IF EXISTS `policy_info`;
CREATE TABLE `policy_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `item_id` bigint(20) NOT NULL COMMENT '对象id',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `sort` int(11) NOT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房间信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for province_info
-- ----------------------------
DROP TABLE IF EXISTS `province_info`;
CREATE TABLE `province_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '省份id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '省份名称',
  `country_id` bigint(20) NOT NULL COMMENT '国家id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for record_info
-- ----------------------------
DROP TABLE IF EXISTS `record_info`;
CREATE TABLE `record_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `author_id` bigint(20) NOT NULL COMMENT '发布者id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片',
  `like` int(11) NOT NULL DEFAULT 0 COMMENT '喜欢',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '景点信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for room_info
-- ----------------------------
DROP TABLE IF EXISTS `room_info`;
CREATE TABLE `room_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `number` int(11) NOT NULL COMMENT '房间数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房间信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for route_info
-- ----------------------------
DROP TABLE IF EXISTS `route_info`;
CREATE TABLE `route_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `item_id` bigint(20) NOT NULL COMMENT '攻略id',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `sort` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '攻略标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for strategy_fee_info
-- ----------------------------
DROP TABLE IF EXISTS `strategy_fee_info`;
CREATE TABLE `strategy_fee_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `strategy_id` bigint(20) NOT NULL COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '攻略描述',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '攻略费用说明' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for strategy_info
-- ----------------------------
DROP TABLE IF EXISTS `strategy_info`;
CREATE TABLE `strategy_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `province` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经度',
  `start_time` date NOT NULL COMMENT '开始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '攻略描述',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '价格',
  `sale` int(11) NOT NULL DEFAULT 0 COMMENT '销售数量',
  `rating` decimal(2, 1) NOT NULL COMMENT '评分',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '攻略信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for strategy_lable
-- ----------------------------
DROP TABLE IF EXISTS `strategy_lable`;
CREATE TABLE `strategy_lable`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `strategy_id` bigint(20) NOT NULL COMMENT '攻略id',
  `lable_id` bigint(20) NOT NULL COMMENT '标签id',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '攻略标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for strategy_order_info
-- ----------------------------
DROP TABLE IF EXISTS `strategy_order_info`;
CREATE TABLE `strategy_order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `strategy_id` bigint(20) NOT NULL COMMENT '攻略id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `number` int(11) NOT NULL COMMENT '数量',
  `appointment_time` date NULL DEFAULT NULL COMMENT '下单时间',
  `price` decimal(10, 2) NOT NULL COMMENT '总价格',
  `payment_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付类型',
  `state` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态：0未使用，1：已使用，2：已过期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '攻略订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号码（用做登录用户名）',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像URL',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '账号状态（1:正常，0:禁用）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除（1:已删除，0:未删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
