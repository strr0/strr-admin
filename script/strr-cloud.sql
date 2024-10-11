DROP DATABASE IF EXISTS `strr-cloud`;

CREATE DATABASE `strr-cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `strr-cloud`;

-- ----------------------------
-- Table structure for dms_column
-- ----------------------------
DROP TABLE IF EXISTS `dms_column`;
CREATE TABLE `dms_column`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint NULL DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `form` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `order` int NULL DEFAULT NULL COMMENT '排序',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dms_module
-- ----------------------------
DROP TABLE IF EXISTS `dms_module`;
CREATE TABLE `dms_module`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` int NULL DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块编码',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模块' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dms_table
-- ----------------------------
DROP TABLE IF EXISTS `dms_table`;
CREATE TABLE `dms_table`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名称',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表描述',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型 (D.目录 M.菜单 B.按钮)',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由组件',
  `i18n_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国际化key',
  `parent_id` int NULL DEFAULT NULL COMMENT '父菜单',
  `frame` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否外链',
  `cache` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否缓存',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `icon_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标类型',
  `order` int NULL DEFAULT NULL COMMENT '排序',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否展示',
  `perms` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, '首页', 'M', 'home', 'home/index', 'route.home', 0, NULL, NULL, 'mdi:monitor-dashboard', '1', NULL, '1', NULL, NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (2, '系统管理', 'D', 'system', NULL, 'route.system', 0, NULL, NULL, 'carbon:cloud-service-management', '1', NULL, '1', NULL, NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (3, '用户管理', 'M', 'user', 'system/user/index', 'route.user', 2, NULL, NULL, 'ic:round-manage-accounts', '1', NULL, '1', 'system:user:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (4, '查看', 'B', NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, '0', 'system:user:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (5, '添加', 'B', NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, '0', 'system:user:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (6, '修改', 'B', NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, '0', 'system:user:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (7, '删除', 'B', NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, '0', 'system:user:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (8, '角色管理', 'M', 'role', 'system/role/index', 'route.role', 2, NULL, NULL, 'carbon:user-role', '1', NULL, '1', 'system:role:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (9, '权限', 'B', NULL, NULL, NULL, 8, NULL, NULL, NULL, NULL, NULL, '0', 'system:role:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (10, '添加', 'B', NULL, NULL, NULL, 8, NULL, NULL, NULL, NULL, NULL, '0', 'system:role:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (11, '修改', 'B', NULL, NULL, NULL, 8, NULL, NULL, NULL, NULL, NULL, '0', 'system:role:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (12, '删除', 'B', NULL, NULL, NULL, 8, NULL, NULL, NULL, NULL, NULL, '0', 'system:role:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (13, '资源管理', 'M', 'resource', 'system/resource/index', 'route.resource', 2, NULL, NULL, 'material-symbols:route', '1', NULL, '1', 'system:resource:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (14, '查看', 'B', NULL, NULL, NULL, 13, NULL, NULL, NULL, NULL, NULL, '0', 'system:resource:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (15, '添加', 'B', NULL, NULL, NULL, 13, NULL, NULL, NULL, NULL, NULL, '0', 'system:resource:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (16, '修改', 'B', NULL, NULL, NULL, 13, NULL, NULL, NULL, NULL, NULL, '0', 'system:resource:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (17, '删除', 'B', NULL, NULL, NULL, 13, NULL, NULL, NULL, NULL, NULL, '0', 'system:resource:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (18, '字典管理', 'M', 'dict', 'system/dict/index', 'route.dict', 2, '0', '0', 'material-symbols:route', '1', 0, '1', 'system:dict:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (19, '字典数据', 'M', 'dictData/:type', 'system/dict-data/index', 'route.dict-data', 18, '0', '0', NULL, '1', 0, '0', 'system:dict:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (20, '查看', 'B', NULL, NULL, NULL, 18, NULL, NULL, NULL, '1', NULL, NULL, 'system:dict:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (21, '添加', 'B', NULL, NULL, NULL, 18, NULL, NULL, NULL, '1', NULL, NULL, 'system:dict:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (22, '修改', 'B', NULL, NULL, NULL, 18, NULL, NULL, NULL, '1', NULL, NULL, 'system:dict:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (23, '删除', 'B', NULL, NULL, NULL, 18, NULL, NULL, NULL, '1', NULL, NULL, 'system:dict:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (24, '客户端管理', 'M', 'oauthClient', 'system/oauth-client/index', 'route.oauth-client', 2, '0', '0', 'material-symbols:route', '1', 0, '1', 'system:client:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (25, '查看', 'B', NULL, NULL, NULL, 24, NULL, NULL, NULL, '1', NULL, NULL, 'system:client:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (26, '添加', 'B', NULL, NULL, NULL, 24, NULL, NULL, NULL, '1', NULL, NULL, 'system:client:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (27, '修改', 'B', NULL, NULL, NULL, 24, NULL, NULL, NULL, '1', NULL, NULL, 'system:client:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (28, '删除', 'B', NULL, NULL, NULL, 24, NULL, NULL, NULL, '1', NULL, NULL, 'system:client:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (29, '文件管理', 'M', 'oss', 'system/oss/index', 'route.oss', 2, '0', '0', 'material-symbols:route', '1', 0, '1', 'system:oss:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (30, '配置管理', 'M', 'ossConfig', 'system/oss-config/index', 'route.oss-config', 29, '0', '0', '', '1', 0, '0', 'system:ossConfig:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (31, '查看', 'B', NULL, NULL, NULL, 30, NULL, NULL, NULL, '1', NULL, NULL, 'system:ossConfig:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (32, '添加', 'B', NULL, NULL, NULL, 30, NULL, NULL, NULL, '1', NULL, NULL, 'system:ossConfig:save', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (33, '修改', 'B', NULL, NULL, NULL, 30, NULL, NULL, NULL, '1', NULL, NULL, 'system:ossConfig:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (34, '删除', 'B', NULL, NULL, NULL, 30, NULL, NULL, NULL, '1', NULL, NULL, 'system:ossConfig:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (35, '上传', 'B', NULL, NULL, NULL, 29, NULL, NULL, NULL, '1', NULL, NULL, 'system:oss:upload', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (36, '下载', 'B', NULL, NULL, NULL, 29, NULL, NULL, NULL, '1', NULL, NULL, 'system:oss:download', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (37, '删除', 'B', NULL, NULL, NULL, 29, NULL, NULL, NULL, '1', NULL, NULL, 'system:oss:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (38, '数据管理', 'D', 'data', NULL, 'route.data', 0, NULL, '0', 'icon-park-outline:all-application', '1', 0, '1', NULL, NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (39, '模块管理', 'M', 'module', 'data/module/index', 'route.module', 38, '0', '0', 'material-symbols:route', '1', 0, '1', 'data:module:list', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (40, '模块详情', 'M', 'moduleItem/:id', 'data/module-item/index', 'route.module-item', 39, '0', '0', NULL, '1', 0, '0', 'data:module:update', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (41, '数据详情', 'M', 'dataItem/:id', 'data/data-item/index', 'route.data-item', 39, '0', '0', NULL, '1', 0, '0', 'data:module:query', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (42, '导入', 'B', NULL, NULL, NULL, 39, NULL, NULL, NULL, NULL, NULL, '0', 'data:module:import', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (43, '删除', 'B', NULL, NULL, NULL, 39, NULL, NULL, NULL, NULL, NULL, '0', 'data:module:remove', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (44, '注册', 'B', NULL, NULL, NULL, 39, NULL, NULL, NULL, NULL, NULL, '0', 'data:module:register', NULL, '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'ROLE_ADMIN', NULL, '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int NULL DEFAULT NULL COMMENT '资源id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色资源关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES (1, 1);
INSERT INTO `sys_role_resource` VALUES (1, 2);
INSERT INTO `sys_role_resource` VALUES (1, 3);
INSERT INTO `sys_role_resource` VALUES (1, 4);
INSERT INTO `sys_role_resource` VALUES (1, 5);
INSERT INTO `sys_role_resource` VALUES (1, 6);
INSERT INTO `sys_role_resource` VALUES (1, 7);
INSERT INTO `sys_role_resource` VALUES (1, 8);
INSERT INTO `sys_role_resource` VALUES (1, 9);
INSERT INTO `sys_role_resource` VALUES (1, 10);
INSERT INTO `sys_role_resource` VALUES (1, 11);
INSERT INTO `sys_role_resource` VALUES (1, 12);
INSERT INTO `sys_role_resource` VALUES (1, 13);
INSERT INTO `sys_role_resource` VALUES (1, 14);
INSERT INTO `sys_role_resource` VALUES (1, 15);
INSERT INTO `sys_role_resource` VALUES (1, 16);
INSERT INTO `sys_role_resource` VALUES (1, 17);
INSERT INTO `sys_role_resource` VALUES (1, 18);
INSERT INTO `sys_role_resource` VALUES (1, 19);
INSERT INTO `sys_role_resource` VALUES (1, 20);
INSERT INTO `sys_role_resource` VALUES (1, 21);
INSERT INTO `sys_role_resource` VALUES (1, 22);
INSERT INTO `sys_role_resource` VALUES (1, 23);
INSERT INTO `sys_role_resource` VALUES (1, 24);
INSERT INTO `sys_role_resource` VALUES (1, 25);
INSERT INTO `sys_role_resource` VALUES (1, 26);
INSERT INTO `sys_role_resource` VALUES (1, 27);
INSERT INTO `sys_role_resource` VALUES (1, 28);
INSERT INTO `sys_role_resource` VALUES (1, 29);
INSERT INTO `sys_role_resource` VALUES (1, 30);
INSERT INTO `sys_role_resource` VALUES (1, 31);
INSERT INTO `sys_role_resource` VALUES (1, 32);
INSERT INTO `sys_role_resource` VALUES (1, 33);
INSERT INTO `sys_role_resource` VALUES (1, 34);
INSERT INTO `sys_role_resource` VALUES (1, 35);
INSERT INTO `sys_role_resource` VALUES (1, 36);
INSERT INTO `sys_role_resource` VALUES (1, 37);
INSERT INTO `sys_role_resource` VALUES (1, 38);
INSERT INTO `sys_role_resource` VALUES (1, 39);
INSERT INTO `sys_role_resource` VALUES (1, 40);
INSERT INTO `sys_role_resource` VALUES (1, 41);
INSERT INTO `sys_role_resource` VALUES (1, 42);
INSERT INTO `sys_role_resource` VALUES (1, 43);
INSERT INTO `sys_role_resource` VALUES (1, 44);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$HLpbo23NoKfxTKuv5UAaB.KMCNvXoCPXDXUKlnZUBQlmhrihU.b2S', '管理员', NULL, 'admin@example.com', NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `label` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client`;
CREATE TABLE `sys_oauth_client`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端id',
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密钥',
  `grant_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权方式',
  `scope` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作用域',
  `redirect_uri` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回调地址',
  `access_timeout` bigint NULL DEFAULT NULL,
  `refresh_timeout` bigint NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oauth_client
-- ----------------------------
INSERT INTO `sys_oauth_client` VALUES (1, 'client', '$2a$10$OwiieKeJGCMZHj3aR5x.ieshDi9F2a4s0bJQADZ2QfdLYStLOc.zO', 'authorization_code,refresh_token,client_credentials,password', 'openid,profile,resource.all', 'http://127.0.0.1:8080/login/oauth2/code/default-client', 3600, 604800, '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'minio' COMMENT '服务商',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '原名',
  `file_suffix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件后缀名',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'URL地址',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对象存储' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主建',
  `config_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '配置key',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'accessKey',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '秘钥',
  `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '桶名称',
  `prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '前缀',
  `endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '访问站点',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '自定义域名',
  `is_https` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否https（Y.是 N.否）',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '域',
  `access_policy` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '桶权限类型（0.private 1.public 2.custom）',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对象存储配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
INSERT INTO `sys_oss_config` VALUES (1, 'minio', 'admin', 'password', 'strr', '', 'localhost:9000', '', '0', '', '1', '', '1', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
