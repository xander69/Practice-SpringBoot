create table users
(
    id              bigint not null primary key,
    username        varchar(255),
    password        varchar(255),
    email           varchar(255),
    active          boolean,
    activation_code varchar(255),
    create_dt       timestamp,
    change_dt       timestamp
);

create sequence user_sequence start 1 increment 1;

create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
);

alter table if exists user_role
    add constraint user_role_usr_fk
        foreign key (user_id) references users;

create table scenario
(
    id            bigint not null primary key,
    name          varchar(255),
    descr         varchar(2000),
    icon_filename varchar(255),
    create_dt     timestamp,
    change_dt     timestamp,
    user_id       bigint
);

create sequence scenario_sequence start 1 increment 1;

alter table if exists scenario
    add constraint scenario_user_fk
        foreign key (user_id) references users on delete set null;