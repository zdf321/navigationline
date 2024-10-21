SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS nav;
USE nav;

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐值',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '行业',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司',
  `profession` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职业',
  `education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学历',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人简介',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_account
-- ----------------------------

-- ----------------------------
-- Table structure for tb_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth`;
CREATE TABLE `tb_auth`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `auth_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限点key值',
  `auth_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限点名称',
  `auth_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限点描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限点表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_auth
-- ----------------------------
INSERT INTO `tb_auth` VALUES ('1', 'space_add', '新建团队空间', NULL);
INSERT INTO `tb_auth` VALUES ('2', 'space_member_manage', '空间成员管理', NULL);
INSERT INTO `tb_auth` VALUES ('3', 'space_setting', '空间设置', NULL);
INSERT INTO `tb_auth` VALUES ('4', 'team_invite_member', '邀请团队成员', NULL);
INSERT INTO `tb_auth` VALUES ('5', 'team_manage', '管理后台', NULL);

-- ----------------------------
-- Table structure for tb_nav
-- ----------------------------
DROP TABLE IF EXISTS `tb_nav`;
CREATE TABLE `tb_nav`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `space_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间id',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `pid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `desc` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `data_type` int NULL DEFAULT NULL COMMENT '数据类型',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `hits_num` int NULL DEFAULT 0 COMMENT '点击量',
  `collect_num` int NULL DEFAULT 0 COMMENT '收藏量',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签，多个用英文逗号隔开',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户nav表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_nav
-- ----------------------------

-- ----------------------------
-- Table structure for tb_nav_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_nav_collect`;
CREATE TABLE `tb_nav_collect`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `nav_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'nav id',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '我的收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_nav_collect
-- ----------------------------

-- ----------------------------
-- Table structure for tb_nav_common_use
-- ----------------------------
DROP TABLE IF EXISTS `tb_nav_common_use`;
CREATE TABLE `tb_nav_common_use`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `nav_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'nav id',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid_index`(`uid` ASC) USING BTREE,
  INDEX `team_id_index`(`team_id` ASC) USING BTREE,
  INDEX `nav_id_index`(`nav_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '我的常用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_nav_common_use
-- ----------------------------

-- ----------------------------
-- Table structure for tb_nav_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_nav_file`;
CREATE TABLE `tb_nav_file`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `nav_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'nav id',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_nav_file
-- ----------------------------

-- ----------------------------
-- Table structure for tb_nav_open_history
-- ----------------------------
DROP TABLE IF EXISTS `tb_nav_open_history`;
CREATE TABLE `tb_nav_open_history`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `nav_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'nav id',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_uid`(`uid` ASC) USING BTREE,
  INDEX `index_team_id`(`team_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '打开的历史记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_nav_open_history
-- ----------------------------

-- ----------------------------
-- Table structure for tb_nav_website
-- ----------------------------
DROP TABLE IF EXISTS `tb_nav_website`;
CREATE TABLE `tb_nav_website`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `nav_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'nav id',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网站地址',
  `logo_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'logo地址',
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '网站长描述',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '网站信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_nav_website
-- ----------------------------

-- ----------------------------
-- Table structure for tb_search_engine
-- ----------------------------
DROP TABLE IF EXISTS `tb_search_engine`;
CREATE TABLE `tb_search_engine`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `is_default` tinyint NULL DEFAULT NULL COMMENT '是否默认',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '搜索引擎配置主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_search_engine
-- ----------------------------
INSERT INTO `tb_search_engine` VALUES ('1', '常用', 1, 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine` VALUES ('2', '搜索', 0, 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine` VALUES ('3', '工具', 0, 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine` VALUES ('4', '社区', 0, 3, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine` VALUES ('5', '图片', 0, 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine` VALUES ('6', '电影', 0, 5, '0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tb_search_engine_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_search_engine_detail`;
CREATE TABLE `tb_search_engine_detail`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `search_engine_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主表id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `is_self_website` tinyint NULL DEFAULT NULL COMMENT '是否是搜索本网站内容',
  `search_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '搜索引擎地址',
  `placeholder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '输入提示',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '搜索引擎配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_search_engine_detail
-- ----------------------------
INSERT INTO `tb_search_engine_detail` VALUES ('1', '1', '站内', 1, '', '本站内资源搜索', 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('10', '2', '搜狗', 0, 'https://www.sogou.com/web?query=', NULL, 5, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('11', '2', '今日头条', 0, 'https://so.toutiao.com/search?dvpf=pc&source=input&keyword=', NULL, 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('12', '3', 'tool.lu', 0, 'https://tool.lu/search/?q=', '搜索tool.lu网站内的工具', 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('13', '3', 'atoolbox', 0, 'http://www.atoolbox.net/Search.php?Keyword=', '搜索atoolbox.net网站内的工具', 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('14', '3', '菜鸟工具', 0, 'https://c.runoob.com/index.php?s=', '请输入关键词搜索工具', 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('15', '3', '蛙蛙工具', 0, 'https://www.iamwawa.cn/search.html?q=', '请输入关键词搜索工具', 3, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('16', '3', '即时工具', 0, 'https://www.67tool.com/key/', '输入关键词搜索，如视频编辑、pdf拆分等', 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('17', '4', 'GitHub', 0, 'https://github.com/search?type=repositories&q=', '输入关键词搜索开源项目', 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('18', '4', 'Gitee', 0, 'https://search.gitee.com/?skin=rec&type=repository&q=', '输入关键词搜索开源项目', 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('19', '4', '知乎', 0, 'https://www.zhihu.com/search?type=content&q=', '搜知乎内容', 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('2', '1', '必应', 0, 'https://cn.bing.com/search?q=', NULL, 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('20', '4', '微信', 0, 'https://weixin.sogou.com/weixin?type=2&query=', '搜微信文章', 3, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('21', '4', '微博', 0, 'https://s.weibo.com/weibo/', '搜索微博', 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('22', '4', '豆瓣', 0, 'https://www.douban.com/search?q=', '搜索你感兴趣的内容和人', 5, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('23', '4', '搜外问答', 0, 'https://ask.seowhy.com/search/?q=', '搜索问题、话题', 6, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('24', '5', 'Pexels', 0, 'https://www.pexels.com/zh-cn/search', '搜索免费图片', 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('25', '5', 'Pxhere', 0, 'https://pxhere.com/zh/photos?q=', '搜索图片，免费、无版权、可商用', 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('26', '5', '稿定素材', 0, 'https://sucai.gaoding.com/picture/materials/', '现在开始寻找你喜欢的图片', 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('27', '5', '花瓣网', 0, 'https://huaban.com/search?q=', '搜索你需要的图片', 3, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('28', '5', '图虫创意', 0, 'https://stock.tuchong.com/search?term=', '尝试输入画面描述，如：“适合汽车广告的背景”', 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('29', '5', 'iconfont', 0, 'https://www.iconfont.cn/search/index?q=', '搜索矢量图标', 5, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('3', '1', '百度', 0, 'https://www.baidu.com/s?wd=', '百度一下，你就知道', 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('30', '6', '爱奇艺', 0, 'https://so.iqiyi.com/so/q_', '输入电影、电视剧名称进行搜索', 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('31', '6', '腾讯视频', 0, 'https://v.qq.com/x/search/?q=', '输入电影、电视剧名称进行搜索', 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('32', '6', '优酷', 0, 'https://so.youku.com/search_video/q_', '输入电影、电视剧名称进行搜索', 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('33', '6', 'B站', 0, 'https://search.bilibili.com/all?keyword=', NULL, 3, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('34', '6', '西瓜视频', 0, 'https://www.ixigua.com/search/', NULL, 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('35', '6', '免费电影', 0, 'https://yiqikuailewanshua8.com/search/-------------.html?wd=', NULL, 5, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('4', '1', 'Google', 0, 'https://www.google.com/search?q=', NULL, 3, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('5', '1', '360搜索', 0, 'https://www.so.com/s?q=', NULL, 4, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('6', '2', '必应', 0, 'https://cn.bing.com/search?q=', NULL, 0, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('7', '2', '百度', 0, 'https://www.baidu.com/s?wd=', '百度一下，你就知道', 1, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('8', '2', 'Google', 0, 'https://www.google.com/search?q=', NULL, 2, '0', NULL, NULL, NULL, NULL);
INSERT INTO `tb_search_engine_detail` VALUES ('9', '2', '360搜索', 0, 'https://www.so.com/s?q=', NULL, 3, '0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tb_system_nav
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_nav`;
CREATE TABLE `tb_system_nav`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `pid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'key值',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'icon',
  `data_type` int NULL DEFAULT NULL COMMENT '数据类型',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `hits_num` int NULL DEFAULT NULL COMMENT '点击次数',
  `collect_num` int NULL DEFAULT NULL COMMENT '收藏量',
  `tag_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签id',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统nav表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_system_nav
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team
-- ----------------------------
DROP TABLE IF EXISTS `tb_team`;
CREATE TABLE `tb_team`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队类型',
  `org_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业或组织名称',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者用户id',
  `scale` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '成员规模',
  `business_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业务类型',
  `vip_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'VIP类型',
  `expire_time` datetime NULL DEFAULT NULL COMMENT 'VIP到期时间',
  `max_people` int NULL DEFAULT NULL COMMENT '团队最大成员数',
  `invite_link_open` int NULL DEFAULT NULL COMMENT '是否开启团队邀请链接 0-关闭 1-开启',
  `invite_link_days` int NULL DEFAULT NULL COMMENT '团队邀请链接有效期（天）-1表示永久有效',
  `invite_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队邀请id',
  `invite_link_start_time` datetime NULL DEFAULT NULL COMMENT '团队邀请链接开启时间',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队主表信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team_dept
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_dept`;
CREATE TABLE `tb_team_dept`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父级id',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team_dept
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team_invite_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_invite_info`;
CREATE TABLE `tb_team_invite_info`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `invite_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请id，uuid',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `invite_link_open` int NULL DEFAULT NULL COMMENT '是否开启团队邀请链接 0-关闭 1-开启',
  `invite_link_days` int NULL DEFAULT NULL COMMENT '团队邀请链接有效期（天）-1表示永久有效',
  `invite_link_start_time` datetime NULL DEFAULT NULL COMMENT '团队邀请链接开启时间',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请者id',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队邀请链接信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team_invite_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_member`;
CREATE TABLE `tb_team_member`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '成员名称',
  `join_time` datetime NULL DEFAULT NULL COMMENT '加入时间',
  `join_way` int NULL DEFAULT NULL COMMENT '加入方式，见JoinWayEnum',
  `invite_uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请者',
  `team_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属角色',
  `dept_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属部门id',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标识 0-未删除 1-删除',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队成员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team_member
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team_publish_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_publish_info`;
CREATE TABLE `tb_team_publish_info`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布后的二级域名',
  `published` tinyint NULL DEFAULT NULL COMMENT '发布状态 0-未发布 1-已发布',
  `show_ad` tinyint NULL DEFAULT NULL COMMENT '是否显示广告 0-不显示 1-显示',
  `check_auth` tinyint NULL DEFAULT NULL COMMENT '是否校验权限 0-不校验 1-校验',
  `need_login` tinyint NULL DEFAULT NULL COMMENT '是否需要登录后才能访问 0-否 1-是',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队发布信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_team_publish_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_role_auth`;
CREATE TABLE `tb_team_role_auth`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队角色key',
  `auth_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限点key',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队角色权限配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team_role_auth
-- ----------------------------
INSERT INTO `tb_team_role_auth` VALUES ('1', 'ADMIN', 'space_add');

-- ----------------------------
-- Table structure for tb_team_space
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_space`;
CREATE TABLE `tb_team_space`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `team_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队id',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间描述',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间logo',
  `max_file_count` int NULL DEFAULT NULL COMMENT '空间最大文件数，-1或null时表示不限制',
  `space_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间类型，见TeamSpaceTypeEnum',
  `show_watermark` tinyint NULL DEFAULT NULL COMMENT '是否开启水印',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '启用禁用表示 0-启动 1-禁用',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队空间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team_space
-- ----------------------------

-- ----------------------------
-- Table structure for tb_team_space_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_team_space_member`;
CREATE TABLE `tb_team_space_member`  (
  `id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `space_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间id',
  `uid` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `space_role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '空间角色',
  `join_time` datetime NULL DEFAULT NULL COMMENT '加入时间',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '团队空间成员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_team_space_member
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
