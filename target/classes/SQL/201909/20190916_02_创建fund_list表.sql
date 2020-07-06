CREATE TABLE `fund_list` (
  `fund_code` varchar(200) NOT NULL COMMENT '基金代码',
  `fund_name` varchar(200) DEFAULT NULL COMMENT '基金代码',
  `create_time` varchar(200) DEFAULT NULL COMMENT '数据创建时间',
  `scale` double DEFAULT NULL COMMENT '基金规模(亿元)',
  `error_ranger` double DEFAULT NULL COMMENT '追踪误差(%)',
  PRIMARY KEY (`fund_code`),
  KEY `fundCode_fundName` (`fund_code`,`fund_name`) USING BTREE,
  KEY `fundName` (`fund_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基金主表';