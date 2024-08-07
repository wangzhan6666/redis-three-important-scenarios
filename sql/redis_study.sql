/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3309
 Source Schema         : redis_study

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 12/07/2024 10:28:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_bank
-- ----------------------------
DROP TABLE IF EXISTS `user_bank`;
CREATE TABLE `user_bank`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户Id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `amount` int(0) NOT NULL DEFAULT 0 COMMENT '用户余额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_bank_id_uindex`(`id`) USING BTREE,
  UNIQUE INDEX `user_bank_user_id_uindex`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户余额表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_bank
-- ----------------------------
INSERT INTO `user_bank` VALUES (18, 1, 'wangzhan1', 1000);
INSERT INTO `user_bank` VALUES (19, 2, 'wangzhan2', 1000);
INSERT INTO `user_bank` VALUES (20, 3, 'wangzhan3', 1000);
INSERT INTO `user_bank` VALUES (21, 4, 'wangzhan4', 1000);
INSERT INTO `user_bank` VALUES (22, 5, 'wangzhan5', 1000);
INSERT INTO `user_bank` VALUES (23, 6, 'wangzhan6', 1000);
INSERT INTO `user_bank` VALUES (24, 7, 'wangzhan7', 1000);
INSERT INTO `user_bank` VALUES (25, 8, 'wangzhan8', 1000);
INSERT INTO `user_bank` VALUES (26, 9, 'wangzhan9', 1000);
INSERT INTO `user_bank` VALUES (27, 10, 'wangzhan10', 1000);
INSERT INTO `user_bank` VALUES (28, 11, 'wangzhan11', 1000);
INSERT INTO `user_bank` VALUES (29, 12, 'wangzhan12', 1000);
INSERT INTO `user_bank` VALUES (30, 13, 'wangzhan13', 1000);
INSERT INTO `user_bank` VALUES (31, 14, 'wangzhan14', 1000);
INSERT INTO `user_bank` VALUES (32, 15, 'wangzhan15', 1000);
INSERT INTO `user_bank` VALUES (33, 16, 'wangzhan16', 1000);
INSERT INTO `user_bank` VALUES (34, 17, 'wangzhan17', 1000);
INSERT INTO `user_bank` VALUES (35, 18, 'wangzhan18', 1000);
INSERT INTO `user_bank` VALUES (36, 19, 'wangzhan19', 1000);
INSERT INTO `user_bank` VALUES (37, 20, 'wangzhan20', 1000);
INSERT INTO `user_bank` VALUES (38, 21, 'wangzhan21', 1000);
INSERT INTO `user_bank` VALUES (39, 22, 'wangzhan22', 1000);
INSERT INTO `user_bank` VALUES (40, 23, 'wangzhan23', 1000);
INSERT INTO `user_bank` VALUES (41, 24, 'wangzhan24', 1000);
INSERT INTO `user_bank` VALUES (42, 25, 'wangzhan25', 1000);
INSERT INTO `user_bank` VALUES (43, 26, 'wangzhan26', 1000);
INSERT INTO `user_bank` VALUES (44, 27, 'wangzhan27', 1000);
INSERT INTO `user_bank` VALUES (45, 28, 'wangzhan28', 1000);
INSERT INTO `user_bank` VALUES (46, 29, 'wangzhan29', 1000);
INSERT INTO `user_bank` VALUES (47, 30, 'wangzhan30', 1000);
INSERT INTO `user_bank` VALUES (48, 31, 'wangzhan31', 1000);
INSERT INTO `user_bank` VALUES (49, 32, 'wangzhan32', 1000);
INSERT INTO `user_bank` VALUES (50, 33, 'wangzhan33', 1000);
INSERT INTO `user_bank` VALUES (51, 34, 'wangzhan34', 1000);
INSERT INTO `user_bank` VALUES (52, 35, 'wangzhan35', 1000);
INSERT INTO `user_bank` VALUES (53, 36, 'wangzhan36', 1000);
INSERT INTO `user_bank` VALUES (54, 37, 'wangzhan37', 1000);
INSERT INTO `user_bank` VALUES (55, 38, 'wangzhan38', 1000);
INSERT INTO `user_bank` VALUES (56, 39, 'wangzhan39', 1000);
INSERT INTO `user_bank` VALUES (57, 40, 'wangzhan40', 1000);
INSERT INTO `user_bank` VALUES (58, 41, 'wangzhan41', 1000);
INSERT INTO `user_bank` VALUES (59, 42, 'wangzhan42', 1000);
INSERT INTO `user_bank` VALUES (60, 43, 'wangzhan43', 1000);
INSERT INTO `user_bank` VALUES (61, 44, 'wangzhan44', 1000);
INSERT INTO `user_bank` VALUES (62, 45, 'wangzhan45', 1000);
INSERT INTO `user_bank` VALUES (63, 46, 'wangzhan46', 1000);
INSERT INTO `user_bank` VALUES (64, 47, 'wangzhan47', 1000);
INSERT INTO `user_bank` VALUES (65, 48, 'wangzhan48', 1000);
INSERT INTO `user_bank` VALUES (66, 49, 'wangzhan49', 1000);
INSERT INTO `user_bank` VALUES (67, 50, 'wangzhan50', 1000);
INSERT INTO `user_bank` VALUES (68, 51, 'wangzhan51', 1000);
INSERT INTO `user_bank` VALUES (69, 52, 'wangzhan52', 1000);
INSERT INTO `user_bank` VALUES (70, 53, 'wangzhan53', 1000);
INSERT INTO `user_bank` VALUES (71, 54, 'wangzhan54', 1000);
INSERT INTO `user_bank` VALUES (72, 55, 'wangzhan55', 1000);
INSERT INTO `user_bank` VALUES (73, 56, 'wangzhan56', 1000);
INSERT INTO `user_bank` VALUES (74, 57, 'wangzhan57', 1000);
INSERT INTO `user_bank` VALUES (75, 58, 'wangzhan58', 1000);
INSERT INTO `user_bank` VALUES (76, 59, 'wangzhan59', 1000);
INSERT INTO `user_bank` VALUES (77, 60, 'wangzhan60', 1000);
INSERT INTO `user_bank` VALUES (78, 61, 'wangzhan61', 1000);
INSERT INTO `user_bank` VALUES (79, 62, 'wangzhan62', 1000);
INSERT INTO `user_bank` VALUES (80, 63, 'wangzhan63', 1000);
INSERT INTO `user_bank` VALUES (81, 64, 'wangzhan64', 1000);
INSERT INTO `user_bank` VALUES (82, 65, 'wangzhan65', 1000);
INSERT INTO `user_bank` VALUES (83, 66, 'wangzhan66', 1000);
INSERT INTO `user_bank` VALUES (84, 67, 'wangzhan67', 1000);
INSERT INTO `user_bank` VALUES (85, 68, 'wangzhan68', 1000);
INSERT INTO `user_bank` VALUES (86, 69, 'wangzhan69', 1000);
INSERT INTO `user_bank` VALUES (87, 70, 'wangzhan70', 1000);
INSERT INTO `user_bank` VALUES (88, 71, 'wangzhan71', 1000);
INSERT INTO `user_bank` VALUES (89, 72, 'wangzhan72', 1000);
INSERT INTO `user_bank` VALUES (90, 73, 'wangzhan73', 1000);
INSERT INTO `user_bank` VALUES (91, 74, 'wangzhan74', 1000);
INSERT INTO `user_bank` VALUES (92, 75, 'wangzhan75', 1000);
INSERT INTO `user_bank` VALUES (93, 76, 'wangzhan76', 1000);
INSERT INTO `user_bank` VALUES (94, 77, 'wangzhan77', 1000);
INSERT INTO `user_bank` VALUES (95, 78, 'wangzhan78', 1000);
INSERT INTO `user_bank` VALUES (96, 79, 'wangzhan79', 1000);
INSERT INTO `user_bank` VALUES (97, 80, 'wangzhan80', 1000);
INSERT INTO `user_bank` VALUES (98, 81, 'wangzhan81', 1000);
INSERT INTO `user_bank` VALUES (99, 82, 'wangzhan82', 1000);
INSERT INTO `user_bank` VALUES (100, 83, 'wangzhan83', 1000);
INSERT INTO `user_bank` VALUES (101, 84, 'wangzhan84', 1000);
INSERT INTO `user_bank` VALUES (102, 85, 'wangzhan85', 1000);
INSERT INTO `user_bank` VALUES (103, 86, 'wangzhan86', 1000);
INSERT INTO `user_bank` VALUES (104, 87, 'wangzhan87', 1000);
INSERT INTO `user_bank` VALUES (105, 88, 'wangzhan88', 1000);
INSERT INTO `user_bank` VALUES (106, 89, 'wangzhan89', 1000);
INSERT INTO `user_bank` VALUES (107, 90, 'wangzhan90', 1000);
INSERT INTO `user_bank` VALUES (108, 91, 'wangzhan91', 1000);
INSERT INTO `user_bank` VALUES (109, 92, 'wangzhan92', 1000);
INSERT INTO `user_bank` VALUES (110, 93, 'wangzhan93', 1000);
INSERT INTO `user_bank` VALUES (111, 94, 'wangzhan94', 1000);
INSERT INTO `user_bank` VALUES (112, 95, 'wangzhan95', 1000);
INSERT INTO `user_bank` VALUES (113, 96, 'wangzhan96', 1000);
INSERT INTO `user_bank` VALUES (114, 97, 'wangzhan97', 1000);
INSERT INTO `user_bank` VALUES (115, 98, 'wangzhan98', 1000);
INSERT INTO `user_bank` VALUES (116, 99, 'wangzhan99', 1000);
INSERT INTO `user_bank` VALUES (117, 100, 'wangzhan100', 1000);

SET FOREIGN_KEY_CHECKS = 1;
