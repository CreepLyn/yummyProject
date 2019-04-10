# Host: localhost  (Version 5.7.20)
# Date: 2019-03-25 20:35:30
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "address"
#

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `description` varchar(255) DEFAULT NULL COMMENT '详细地址说明',
  `name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `sex` varchar(255) DEFAULT NULL COMMENT '收货人性别',
  `telephone` varchar(255) DEFAULT NULL COMMENT '收货人联系方式',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='收货地址';

#
# Data for table "address"
#

INSERT INTO `address` VALUES (1,2,'南京大学仙林校区6栋','张寅','女士','13805103211'),(3,2,'陶园南楼','张辰溪','男士','1238750876'),(4,2,'南京大学鼓楼校区','张寅','女士','15777293476'),(5,6,'南京大学鼓楼校区2栋','王文王','男士','87673497102');

#
# Structure for table "expressman"
#

DROP TABLE IF EXISTS `expressman`;
CREATE TABLE `expressman` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `state` varchar(255) DEFAULT NULL COMMENT '空闲状态',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='派送员信息表';

#
# Data for table "expressman"
#

INSERT INTO `expressman` VALUES (1,'王五','878787878','free');

#
# Structure for table "food"
#

DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `menuid` int(11) DEFAULT NULL COMMENT '菜单ID',
  `name` varchar(255) DEFAULT NULL COMMENT '食物名',
  `num` int(11) NOT NULL DEFAULT '1' COMMENT '库存数量',
  `price` double(11,2) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='食物';

#
# Data for table "food"
#

INSERT INTO `food` VALUES (1,1,'原味吮指鸡',8,11.00),(2,1,'黄金鸡块',10,11.00),(3,2,'香辣鸡腿堡',10,19.00),(4,2,'劲脆鸡腿堡',9,17.00),(5,2,'新奥尔良鸡腿堡',10,22.00),(6,3,'新春套餐',5,74.00),(7,4,'老北京鸡肉卷',7,15.00),(8,5,'薯霸王（大）',20,15.00),(9,6,'霸辣鸡腿堡套餐',4,34.00),(10,5,'狠霸王牛堡',5,22.00),(11,5,'王道鲜嫩鸡块',14,11.50),(12,7,'奶茶三兄弟',10,13.00),(13,7,'红豆奶茶（中）',10,10.00),(14,7,'芒果欧蕾',9,13.00),(15,8,'焦糖奶茶',5,6.50),(16,8,'金桔柠檬',5,6.00),(17,9,'香草拿铁',9,20.00),(18,9,'美式咖啡',10,13.00),(19,10,'我家牛油菠萝包',20,12.00),(20,10,'我家叉烧包',10,13.00),(21,13,'葡式蛋挞',10,11.00),(22,14,'铁板黑椒牛仔骨',5,58.00),(23,14,'干锅牛蛙',5,68.00),(24,14,'港式砂锅大鱼头',5,85.00),(25,15,'蜜汁叉烧',10,49.00),(26,12,'云南菠萝切250g',10,18.89),(27,12,'奶油草莓250g',10,15.80),(28,16,'芒果+西瓜',10,27.80),(29,16,'芒果+凤梨',10,27.80),(30,16,'西瓜+哈密瓜',10,18.80),(31,17,'芒果酸奶捞约480g',10,38.90),(32,17,'火龙果酸奶捞约480g',10,37.80),(33,18,'西瓜汁（纯）',10,18.89),(34,20,'饭',20,2.00);

#
# Structure for table "hibernate_sequence"
#

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Data for table "hibernate_sequence"
#

/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

#
# Structure for table "menu"
#

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `shopid` int(11) DEFAULT NULL COMMENT '店铺ID',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='菜单';

#
# Data for table "menu"
#

INSERT INTO `menu` VALUES (1,1,'热销'),(2,1,'汉堡'),(3,1,'优惠套餐'),(4,1,'小吃'),(5,2,'热销'),(6,2,'套餐'),(7,3,'热销'),(8,3,'优惠'),(9,3,'咖啡时光'),(10,4,'热销'),(11,5,'热销'),(12,6,'热销'),(13,4,'优惠'),(14,4,'无荤不欢'),(15,4,'风味卤烧'),(16,6,'双拼组合'),(17,6,'酸奶捞'),(18,6,'鲜榨果汁'),(19,7,'热销'),(20,7,'主食');

#
# Structure for table "off"
#

DROP TABLE IF EXISTS `off`;
CREATE TABLE `off` (
  `id` bigint(20) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `off_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`off_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Data for table "off"
#

/*!40000 ALTER TABLE `off` DISABLE KEYS */;
INSERT INTO `off` VALUES (1,'12','50'),(1,'20','80'),(2,'10','40'),(2,'25','95'),(3,'5','25'),(3,'8','35'),(4,'10','140'),(4,'4','40'),(6,'18','29'),(6,'35','60'),(7,'2','20');
/*!40000 ALTER TABLE `off` ENABLE KEYS */;

#
# Structure for table "orderinfo"
#

DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) DEFAULT NULL COMMENT '订单号',
  `foodid` int(11) DEFAULT NULL COMMENT '食物ID',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='订单内容';

#
# Data for table "orderinfo"
#

INSERT INTO `orderinfo` VALUES (1,1,1,2),(2,1,4,1),(3,2,9,1),(4,2,11,1),(5,3,9,1),(6,3,10,1),(7,4,6,1),(8,5,14,1),(9,5,17,1),(10,6,34,10),(11,7,19,1),(12,7,20,1),(13,8,12,2),(14,8,14,1);

#
# Structure for table "orders"
#

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `userid` int(11) DEFAULT NULL COMMENT '消费者ID',
  `shopid` int(11) DEFAULT NULL COMMENT '商铺ID',
  `date` varchar(255) DEFAULT NULL COMMENT '订单日期',
  `price` double(11,2) DEFAULT NULL COMMENT '支付金额',
  `state` varchar(255) DEFAULT NULL COMMENT '订单状态',
  `expressmanid` int(11) DEFAULT NULL COMMENT '配送员ID',
  `addressid` int(11) DEFAULT NULL COMMENT '收货地址ID',
  `off` int(11) DEFAULT NULL,
  `redoff` int(11) DEFAULT NULL COMMENT '红包数额',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='订单';

#
# Data for table "orders"
#

INSERT INTO `orders` VALUES (1,'1201903052219352',2,1,'2019-03-05 22:19:35',48.00,'已完成',7,3,0,0),(2,'1201903151512392',2,2,'2019-03-15 15:12:39',39.00,'已完成',7,4,10,0),(3,'1201903152142072',2,2,'2019-03-15 21:42:07',39.50,'已取消',7,1,10,10),(4,'1201903161438536',6,1,'2019-03-16 14:38:53',61.00,'已取消',7,5,12,10),(5,'1201903161559406',6,3,'2019-03-16 15:59:40',35.50,'已完成',7,5,5,0),(6,'1201903251601492',2,7,'2019-03-25 16:01:49',22.00,'已完成',7,1,0,NULL),(7,'1201903251602232',2,4,'2019-03-25 16:02:23',32.50,'已取消',7,3,0,NULL),(8,'1201903251602422',2,3,'2019-03-25 16:02:42',38.50,'已送达',7,1,8,NULL);

#
# Structure for table "redpacket"
#

DROP TABLE IF EXISTS `redpacket`;
CREATE TABLE `redpacket` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `bonus` int(11) DEFAULT NULL COMMENT '红包金额',
  `price` int(11) DEFAULT NULL COMMENT '红包金额条件',
  `type` varchar(255) DEFAULT NULL COMMENT '适用类型',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='红包信息表';

#
# Data for table "redpacket"
#

INSERT INTO `redpacket` VALUES (5,6,10,35,'快餐便当'),(6,2,10,35,'快餐便当'),(7,2,15,28,'特色菜系');

#
# Structure for table "settlement"
#

DROP TABLE IF EXISTS `settlement`;
CREATE TABLE `settlement` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL COMMENT '结算日期',
  `orderid` int(11) DEFAULT NULL COMMENT '最后一个订单号',
  `profit` double(11,2) DEFAULT NULL COMMENT '利润',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='结算表';

#
# Data for table "settlement"
#

INSERT INTO `settlement` VALUES (1,'2019-03-09',1,4.80),(2,'2019-03-25 04:05:11',1,26.80);

#
# Structure for table "shop"
#

DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '店主ID',
  `shopname` varchar(255) DEFAULT NULL COMMENT '商铺名',
  `address` varchar(255) DEFAULT NULL COMMENT '商铺地址',
  `start_time` varchar(255) NOT NULL DEFAULT '08:00:00' COMMENT '开始营业时间',
  `end_time` varchar(255) NOT NULL DEFAULT '21:00:00' COMMENT '结束营业时间',
  `telephone` varchar(255) DEFAULT NULL COMMENT '店铺电话',
  `mini_price` int(11) NOT NULL DEFAULT '0' COMMENT '起送价',
  `delivery_price` double(11,1) DEFAULT '0.0' COMMENT '配送费',
  `state` varchar(255) DEFAULT NULL COMMENT '店铺状态',
  `packing_price` double(11,1) NOT NULL DEFAULT '0.0' COMMENT '包装费',
  `type` varchar(255) DEFAULT NULL COMMENT '商店类型',
  `description` varchar(255) DEFAULT NULL COMMENT '店铺简介',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='商铺';

#
# Data for table "shop"
#

INSERT INTO `shop` VALUES (1,3,'kfc','南京市鼓楼区广州路46号','08:00:00','21:00:00','0123-1237690',25,9.0,'已通过',0.0,'快餐便当','本餐厅不使用饿了么配送，由肯德基宅急送官方品牌配送，会员用户无法享受免配送费服务'),(2,4,'汉堡王','南京市紫峰大厦','08:00:00','21:00:00','88887878',25,3.5,'已通过',0.0,'快餐便当','汉堡王啦啦啦'),(3,5,'COCO都可（汉口路店）','南京市鼓楼区汉口路47号之2门面房','10:00:00','20:30:00','87197568934',20,3.5,'已通过',2.0,'甜品饮品','一起了解CoCo！为您严选优质茶叶以及在地化采购季节鲜果并且不断创新研发严格食安控管所制成的健康茶饮!'),(4,7,'避风塘（南京新百店）','南京市秦淮区中山南路1号新百商店八层801商铺','10:00:00','21:00:00','878788645',20,3.5,'已通过',2.0,'特色菜系','上海避风塘美食有限公司创立于1998年，主营港式特色菜肴和点心。以「美味」、「专注多元化」、「始终如 一」、「大众化」及「随时随地」的「精致美食触手可及」理念。'),(6,9,'果缤纷水果','南京市秦淮区洪武路285号','10:00:00','23:30:00','84659716',20,0.5,'已通过',2.0,'果蔬生鲜','果缤纷水果;一家只做水果拼盘的外送店;标准流程;匠心品质;切片水果均为订购后现切;新鲜度100%;本店所有水果可包退;包换;包赔;如偶然有质量问题请联系我们的售后：17366335719;一份新鲜代表了一份诚心~~'),(7,11,'绿茶','123','15:58:40','15:58:40','123',20,0.0,'已通过',2.0,'特色菜系','ad时间分给');

#
# Structure for table "track"
#

DROP TABLE IF EXISTS `track`;
CREATE TABLE `track` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) DEFAULT NULL COMMENT '订单号',
  `place_time` varchar(255) DEFAULT NULL COMMENT '下单时间',
  `pay_time` varchar(255) DEFAULT NULL COMMENT '支付时间',
  `receive_time` varchar(255) DEFAULT NULL COMMENT '接单时间',
  `deliver_time` varchar(255) DEFAULT NULL COMMENT '配送时间',
  `arrive_time` varchar(255) DEFAULT NULL COMMENT '送达时间',
  `cancel_time` varchar(255) DEFAULT NULL COMMENT '取消时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='订单跟踪状态';

#
# Data for table "track"
#

INSERT INTO `track` VALUES (1,1,'22:19:','22:19:','22:19:40','22:23:17','22:55:04',NULL),(2,2,'15:12:39','15:12:44','15:13:10','15:13:40','15:13:40',NULL),(3,3,'21:42:07',NULL,NULL,NULL,NULL,'21:44:04'),(4,4,'14:38:53','14:39:10','14:40:10',NULL,NULL,'14:40:53'),(5,5,'15:59:40','16:00:03','16:00:42','16:03:03','16:09:01',NULL),(6,6,'16:01:49','16:01:49','16:01:49','16:04:49','16:08:01',NULL),(7,7,'16:02:23',NULL,NULL,NULL,NULL,'16:04:23'),(8,8,'16:02:42','16:02:42',NULL,'16:05:42','16:07:42','16:02:57');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `email` varchar(255) DEFAULT NULL COMMENT '用户注册邮箱',
  `balance` double(11,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `telephone` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `level` varchar(255) DEFAULT NULL COMMENT '用户等级',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'admin01','admin','admin@admin.com',0.00,'admin','120876230',NULL),(2,'张三','123','zhangsan@163.com',801.25,'普通用户','13805103210','2'),(3,'王五','wangwu','wangwu@163.com',43.20,'外卖商家','19736245','1'),(4,'mark','mark','mark@mark.com',35.10,'外卖商家','19930923','1'),(5,'jackson@163.com','jackson','jackson@163.com',19.25,'外卖商家','13805123423','1'),(6,'bam@163.com','bambam','bam@163.com',525.50,'普通用户','13777779876','1'),(7,'3538641','luozhixiang','showlo@163.com',0.00,'外卖商家','','1'),(8,'5125615','longjunheng','joker@163.com',0.00,'外卖商家','','1'),(9,'4616604','huachenyu','huahua@163.com',0.00,'外卖商家','','1'),(10,'854277989@qq.com','0818','854277989@qq.com',0.00,'普通用户','','1'),(11,'9670419','0818','creeplyn98zy@163.com',39.60,'外卖商家','','1');
