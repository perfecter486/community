drop table  user;
create table user
(
    account_id  varchar(100) null,
    name        varchar(50)  null,
    token       char(36)     null,
    create_time bigint       null,
    modify_time bigint       null,
    id          int auto_increment
        primary key,
    avatar_url  varchar(256) null
)
    comment '用户信息';

