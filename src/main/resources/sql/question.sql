drop table  question;
create table question
(
    id int auto_increment,
    title varchar(50) not null,
    create_time bigint not null,
    modify_time bigint not null,
    description text null,
    creator int null,
    view_count int default 0 null,
    comment_count int default 0 null,
    like_count int default 0 null,
    tags varchar(256) null,
    constraint question_pk
        primary key (id)
);

