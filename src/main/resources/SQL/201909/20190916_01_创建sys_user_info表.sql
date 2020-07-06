create table sys_user_info
(
  id         VARCHAR(200) comment '用户ID' primary key ,
  name       VARCHAR(200) comment '用户名',
  account    VARCHAR(200) not null comment '账号',
  password   VARCHAR(200) not null comment '密码',
  create_time VARCHAR(200) comment '创建时间',
  last_time   VARCHAR(200) comment '最后操作时间',
  last_login  VARCHAR(200) comment '最后登录时间',
  opt_times   INT comment '操作次数',
  gold       INT default 0 not null comment '金币数',
  level     INT comment '等级',
  img        VARCHAR(2000) comment '头像地址',
  marker     VARCHAR(2000) comment '备注'
) comment='系统用户信息表' default charset=utf8;