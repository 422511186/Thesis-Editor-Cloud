create table authorities
(
    permission_id varchar(32) not null
        primary key,
    value         varchar(40) not null
);

create table role_permissions
(
    id            varchar(32) not null
        primary key,
    role_id       varchar(32) not null,
    permission_id varchar(32) null
);

create table roles
(
    role_id   varchar(32) not null
        primary key,
    role_name varchar(15) not null
);

create table user
(
    id           varchar(32)   not null comment '主键'
        primary key,
    user_name    varchar(16)   null comment '用户名',
    pass_word    varchar(100)  null comment '密码',
    nick_name    varchar(16)   null comment '昵称',
    sex          int default 0 null comment '性别',
    email        varchar(32)   null comment '邮箱',
    phone_number varchar(11)   null comment '手机号码',
    status       int default 0 null comment '账号状态',
    constraint user_user_name_uindex
        unique (user_name)
);

create table user_role
(
    id      int auto_increment
        primary key,
    user_id varchar(32) not null,
    role_id varchar(32) not null
);

