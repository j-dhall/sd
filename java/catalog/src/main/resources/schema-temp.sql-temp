create table if not exists category (
	id integer not null auto_increment,
    name varchar(128) not null,
    primary key (id)
);

create table if not exists sub_category (
	id integer not null auto_increment,
    category_id integer not null,
    name varchar(128) not null,
    primary key (id),
    foreign key (category_id) references category(id)
);

create table if not exists product (
	id integer not null auto_increment,
    name varchar(128) not null,
    description varchar(128),
    image_path varchar(128),
    category_id integer,
    sub_category_id integer,
    primary key (id),
    foreign key (category_id) references category(id),
    foreign key (sub_category_id) references sub_category(id)
);