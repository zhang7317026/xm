CREATE TABLE `index_list` (
  `indexCode` varchar(255) NOT NULL COMMENT '指数编码',
  `indexName` varchar(255) DEFAULT NULL COMMENT '指数名称',
  `createTime` varchar(255) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`indexCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;