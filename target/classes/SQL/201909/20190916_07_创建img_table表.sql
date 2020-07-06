create table img_table
(
  id         VARCHAR(200) comment '主键' primary key,
  file_name   VARCHAR(200) comment '文件名称',
  url        VARCHAR(200) comment 'URL地址',
  create_time VARCHAR(200) comment '创建时间',
  creater    VARCHAR(200) comment '创建人'
) comment '图片表' default charset=utf8;