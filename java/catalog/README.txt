Schema:
classpath:db/migration/V1_1__initial.sql

Use Cases:
CREATE
1. Add Product [+Category][+SubCategory] New; New:New; Old; Old:New; Old:Old
	Not allowed: New:Old
*2. Add Category
*3. Add SubCategory of Category New:New; Old:New

UPDATE
1. Update Product
2. Update Product Category [+SubCategory] Null->Old; Null:Null->Old:Old;
3. Update Category
4. Update SubCategory

DELETE
1. Delete Product
2. Delete SubCategory
3. Delete Category

READ
1. Get Product
2. Get Category [+SubCategories]
3. Get SubCategory
4. Get All Products
5. Get All Categories
6. Get All SubCategories of Category
7. Get Products of Category [+SubCategory/+SubCategories]
8. Get Products of Categories
9. Get Products by text