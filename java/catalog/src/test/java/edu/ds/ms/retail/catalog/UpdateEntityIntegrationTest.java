package edu.ds.ms.retail.catalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.service.CategoryService;
import edu.ds.ms.retail.catalog.service.ProductService;
import edu.ds.ms.retail.catalog.service.SubCategoryService;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test_data.sql"})
public class UpdateEntityIntegrationTest {
	@Autowired
	CategoryService categoryService;
	@Autowired
	SubCategoryService subCategoryService;
	@Autowired
	ProductService productService;
	
	//UPDATE CATEGORY
	
	//Test: Update Category name to another unique name. Update description.
	@Test
	void testUpdateCategoryNameAndDescription() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		cat.setName("Education Books");//update name
		cat.setDescription("Education Books: " + cat.getDescription());//update description
		
		//Save updated Category
		categoryService.saveCategory(cat);
	}
	
	//Test: Update Category name to an existing name
	//Throws: java.sql.SQLIntegrityConstraintViolationException
	@Test
	void testUpdateCategoryWithDuplicateName() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		cat.setName("Electronics");//update name with existing category name
		
		//Save updated Category
		try {
			categoryService.saveCategory(cat);
		} catch (Exception e) {
			assertTrue(e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException);
		}
	}
	
	//UPDATE SUBCATEGORY
	
	//Test: Update SubCategory name to another unique name. Update description.
	@Test
	void testUpdateSubCategoryDescription() {
		SubCategory subcat = subCategoryService.getSubCategoryByCategoryNameAndSubCategoryName("Electronics", "Televisions");//Existing SubCategory
		assertNotNull(subcat);
		subcat.setDescription("Updated: " + subcat.getDescription());//update description
		
		//Save SubCategory
		subCategoryService.saveSubCategory(subcat);
	}
	
	//Test:
	@Test
	void testUpdateSubCategoryNameUniqueInCategory() {
		SubCategory subcat = subCategoryService.getSubCategoryByCategoryNameAndSubCategoryName("Books", "Literature");//Existing SubCategory
		assertNotNull(subcat);
		subcat.setName("English Literature");//update name
		
		//Save SubCategory
		subCategoryService.saveSubCategory(subcat);
	}
	
	//Test:
	//Throws: 
	@Test
	void testUpdateSubCategoryNameNotUniqueInCategory() {
		SubCategory subcat = subCategoryService.getSubCategoryByCategoryNameAndSubCategoryName("Books", "Literature");//Existing SubCategory
		assertNotNull(subcat);
		subcat.setName("Mathematics");//update name
		
		//Save SubCategory
		subCategoryService.saveSubCategory(subcat);
	}
	
	//Test: For the SubCategory not referenced by any Product, update the Category it belongs to.
	void testUpdateUnReferencedSubCategoryParentCategory() {
		
	}
	
	//Test: For the SubCategory referenced by any Product, try updating the Category it belongs to.
	//This should not be allowed.
	//Throws: 
	void testUpdateReferencedSubCategoryParentCategory() {
		
	}
	
	//UPDATE PRODUCT
	
	//Test: Update Product name, description, image-path
	//Throws:
	@Test
	void test1() {
		
	}
	
	//Test: Update Product SubCategory
	//Throws:
	@Test
	void test2() {
		
	}
	
	//Test: Update Product Category and SubCategory
	//Throws:
	@Test
	void test3() {
		
	}
	
	//Test:
	//Throws:
	@Test
	void test4() {
		
	}
	
	//Test:
	//Throws:
	@Test
	void test5() {
		
	}
	
	//Test:
	//Throws:
	@Test
	void test6() {
		
	}
	
	//Test:
	//Throws:
	@Test
	void test7() {
		
	}
}
