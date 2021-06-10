create table if not exists app_user (
    id bigint,
    username varchar(50) not null,
    password varchar(100) not null,
    role varchar(10) not null,
    primary key (id),
    unique key username (username)
);

create sequence if not exists hibernate_sequence start with 100;
