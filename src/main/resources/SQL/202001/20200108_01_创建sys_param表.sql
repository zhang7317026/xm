CREATE TABLE `sys_param` (
  `param_code` varchar(255) NOT NULL DEFAULT '' COMMENT '编码',
  `param_value` varchar(2000) DEFAULT NULL COMMENT '内容值',
  `param_desc` varchar(255) DEFAULT NULL COMMENT '描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;