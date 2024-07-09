create table contact
(
    cid      int auto_increment
        primary key,
    uid      int                                 not null,
    fid      int                                 not null,
    classify varchar(20)                         null,
    time     timestamp default CURRENT_TIMESTAMP not null,
    status   int       default 0                 not null
);

create table message
(
    mid      int auto_increment
        primary key,
    sender   int                                 not null,
    receiver int                                 not null,
    content  text                                not null,
    time     timestamp default CURRENT_TIMESTAMP not null,
    status   int       default 0                 not null
);

create table user
(
    uid      int auto_increment
        primary key,
    username varchar(20)   null,
    password varchar(255)  null,
    type     int default 0 null,
    status   int default 0 null,
    constraint username
        unique (username)
);

create table userinfo
(
    iid      int auto_increment
        primary key,
    uid      int                                 not null,
    nickname varchar(50)                         null,
    avatar   varchar(200)                        null,
    phone    varchar(50)                         null,
    email    varchar(50)                         null,
    region   varchar(50)                         null,
    register timestamp default CURRENT_TIMESTAMP not null
);

create definer = root@localhost view user_view as
select `demo`.`user`.`uid`          AS `uid`,
       `demo`.`user`.`username`     AS `username`,
       `demo`.`userinfo`.`nickname` AS `nickname`,
       `demo`.`userinfo`.`avatar`   AS `avatar`,
       `demo`.`userinfo`.`email`    AS `email`,
       `demo`.`userinfo`.`phone`    AS `phone`,
       `demo`.`userinfo`.`region`   AS `region`
from (`demo`.`user` join `demo`.`userinfo` on ((`demo`.`user`.`uid` = `demo`.`userinfo`.`uid`)));


