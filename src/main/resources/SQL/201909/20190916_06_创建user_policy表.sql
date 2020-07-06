create table user_policy
(
  policy_id        VARCHAR(200) comment '主键' primary key,
  policy_name      VARCHAR(200) comment '策略名称',
  user_id          VARCHAR(200) comment '用户id',
  input_all        DOUBLE default 0.0 comment '总投入',
  now_all          DOUBLE default 0.0 comment '当前总值',
  surplus          DOUBLE default 0.0 comment '活期余额',
  make_all         DOUBLE default 0.0 comment '总盈利额',
  create_time      VARCHAR(200) comment '创建时间',
  start_date       VARCHAR(200) comment '当前周期开始日期',
  last_date        VARCHAR(200) comment '上一交易日',
  mark             VARCHAR(200) comment '备注',
  surplus_interest DOUBLE default 0.0 comment '活期余额利率',
  input_period	   VARCHAR(200) comment '投入周期',
  input_per        DOUBLE default 0.0 comment '每份投入',
  update_date      VARCHAR(200) comment '更新日期',
  update_time      VARCHAR(200) comment '更新时间',
  status           VARCHAR(200) comment '状态'
) comment '用户对策略表' default charset=utf8;

