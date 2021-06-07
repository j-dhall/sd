package edu.ds.ms.retail.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.service.CategoryService;
import edu.ds.ms.retail.catalog.service.ProductService;
import edu.ds.ms.retail.catalog.service.SubCategoryService;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test_data.sql"})
public class ReadEntityIntegrationTests {
	@Autowired
	CategoryService categoryService;
	@Autowired
	SubCategoryService subCategoryService;
	@Autowired
	ProductService productService;

	@Test
	void testGetAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		
		//Assert
		assertEquals(categories.size(), 2);
	}
	
	@Test
	void testGetAllSubCategories() {
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		
		//Assert
		assertEquals(subCategories.size(), 4);
	}
	
	@Test
	void testGetSubCategoriesByCategoryName() {
		List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryName("Books");
		
		//Assert
		assertEquals(subCategories.size(), 2);
	}
	
	@Test
	void testGetProductByCategoryName() {
		List<Product> products = productService.getProductsByCategoryName("Electronics");//Existing Products
		
		//Assert
		assertEquals(products.size(), 4);
	}

	@Test
	void testGetProductsByCategoryNameAndSubCategoryName() {
		List<Product> products = productService.getProductsByCategoryNameAndSubCategoryName("Electronics", "Speakers");

		//Assert
		assertEquals(products.size(), 2);
	}
	
	@Test
	void testGetProductsByCategoryDescription() {
		List<Product> products = productService.getProductsByDescription("sound");

		//Assert
		//("Bose 5.1 Surround Sound", "Immersive sound experience")
		assertEquals(products.size(), 1);
	}
	
	@Test
	void testGetProductsByText() {
		List<Product> productsTV = productService.getProductsByText("TV");
		List<Product> productsSpeaker = productService.getProductsByText("Speaker");
		
		//Assert
		assertEquals(productsTV.size(), 2);//("Sony HD TV", "40 inch"), ("Samsung 3D TV", "56 inch")
		assertEquals(productsSpeaker.size(), 1);//("Creative 2.1 Computer Speakers", "for Windows 10 and Mac")		
	}
}
