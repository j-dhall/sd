create table if not exists category (id integer not null auto_increment, description varchar(255), name varchar(255) not null, primary key (id));

create table if not exists product (id integer not null auto_increment, description varchar(255), image_path varchar(255), name varchar(255) not null, category_id integer, sub_category_id integer, primary key (id));

create table if not exists sub_category (id integer not null auto_increment, description varchar(255), name varchar(255) not null, category_id integer not null, primary key (id));

alter table category add constraint uk_CATEGORY_name unique (name);

alter table product add constraint fk_PRODUCT_categoryId__CATEGORY_id foreign key (category_id) references category (id);

alter table product add constraint fk_PRODUCT_subCategoryId__SUB_CATEGORY_id foreign key (sub_category_id) references sub_category (id);

alter table sub_category add constraint fk_SUB_CATEGORY_categoryId__CATEGORY_id foreign key (category_id) references category (id);
