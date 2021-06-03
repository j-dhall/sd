#DDL
use catalog;
use catalog2;
drop database catalog;
drop database catalog2;

#DESCRIBE
describe category;
describe sub_category;
describe product;

#SELECT
select * from category;
select * from sub_category;
select * from product;

#COMMIT
commit;

#INSERT
#Categories
insert into category (name, description) values ("Books", "Collection of paperback books, Kindle e-books, periodicals, etc");
insert into category (name, description) values ("Electronics", "Audio/Video Systems, Phones, Cameras, etc");
#SubCategories
insert into sub_category (name, description, category_id) values ("Mathematics", "Books on mathematical topics in Secondary School and Higher Engineering", 1);
insert into sub_category (name, description, category_id) values ("Literature", "Books on languages and art", 1);
insert into sub_category (name, description, category_id) values ("Speakers", "Audio stereo systems, 5.1 surround systems", 2);
insert into sub_category (name, description, category_id) values ("Televisions", "HD and 3D televisions", 2);
#Products
insert into product (name, description, image_path, category_id, sub_category_id) values ("Geometry Std 9", "Lines, Planes, 3D objects", "/geom.png", 1, 1);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Linear Algebra", "Determinants, Eigenvectors, PCA", "/lina.png", 1, 1);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Grammar made easy", "Pronouns, Adverbs", "/gram.png", 1, 2);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Shakespeare", "Hamlet", "/shak.png", 1, 2);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Creative 2.1 Computer Speakers", "for Windows 10 and Mac", "/crea.png", 2, 3);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Bose 5.1 Surround Sound", "Immersive sound experience", "/bose.png", 2, 3);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Sony HD TV", "40 inch", "/sony.png", 2, 4);
insert into product (name, description, image_path, category_id, sub_category_id) values ("Samsung 3D TV", "56 inch", "/sams.png", 2, 4);