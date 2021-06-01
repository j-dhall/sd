package edu.ds.ms.retail.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.repository.ProductRepository;
import edu.ds.ms.retail.catalog.repository.SubCategoryRepository;
import edu.ds.ms.retail.catalog.service.CategoryService;
import edu.ds.ms.retail.catalog.service.ProductService;
import edu.ds.ms.retail.catalog.service.SubCategoryService;

@SpringBootTest
@ActiveProfiles("test") //override application.properties with /src/test/resources/application-test.properties
//@Sql({"/product.sql"}) //use this per test case since we cleanup the inserts from other test cases.
//I DONT KNOW IF RACE CONDITION IS APPLICABLE. What if there is a context switch to another test case after running product.sql
//This race condition problem will not be solved if we move cleanup to after the test case.
//The sql file execution and the test case may not be in the same transaction.

@Transactional //binds the product.sql script and test case database inserts into a transaction. If the test case fails, the data added by product.sql is rolled back.

//It is still better to have a post-test cleanup sql, rather than a pre-test cleanup.
//Even this is not required. @Transactional deletes all data inserted during a test case run.
class SpringBootDBSchemaCreationAndInitialLoadIntegrationTest {
	
	@Autowired
	EntityManager entityManager;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	//test the repository layer. intention was to test integrity constraints
	@Test
	@Sql(scripts = {"/product.sql"})
	//We do not need "product_delete.sql" since @Transactional will delete any data created during the test case run
	//@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/product_delete.sql"})
	void testUsingRepository() {
		Product prod = new Product();
		prod.setName("Echo");
		prod.setDescription("Amazon Echo");
		//try {
			productRepository.save(prod);
			//2 products, 1 from product.sql, another from productRepository.save(prod)
			List<Product> products = productRepository.findAll();
			assertEquals(2, products.size());
		//} catch (Exception e) { //Not useful, see note below
			//NOTE: tried an SQL with foreign key constraints violated, but could not catch SQLIntegrityConstraintViolationException here.
			//The test case fails and SQLIntegrityConstraintViolationException shows up in the log.
			//This is probably due to a repository weapper.
			//Read here: https://stackoverflow.com/questions/54876448/how-to-catch-hibernate-constraintviolationexception-or-spring-dataintegrityviol
			//assertThat(e).isInstanceOf(SQLIntegrityConstraintViolationException.class)
			//.hasMessage("Cannot add or update a child row: a foreign key constraint fails");
		//}	
	}
	
	
	private void checkPersistenceContext(Product prod, Category cat, SubCategory subCat) {
		boolean contains = entityManager.contains(prod);
		contains = entityManager.contains(cat);
		contains = entityManager.contains(subCat);
		contains = false; //dummy for breakpoint
	}
	
	
	//test the service layer. intention was to test integrity constraints
	@Test
	@Sql({"/product.sql"})
	void testUsingService() {
		//create category
		Category cat = new Category();
		cat.setName("Electronics");
		
		//create sub-category
		SubCategory subCat = new SubCategory();
		//subCat.setCategory(cat); //use Category.addSubCategory()
		subCat.setName("Audio Systems");
		
		//create product
		Product prod = new Product();
		prod.setName("Echo");
		prod.setDescription("Amazon Echo");		
		//prod.setCategory(cat); //use Category.addProduct()
		//prod.setSubCategory(subCat); //use SubCategory.addProduct()
		
		//set one-to-many relationships
		cat.addSubCategory(subCat);
		cat.addProduct(prod);
		subCat.addProduct(prod);
		
		//save
		//False Note: Any of the below 3 calls creates the other 2 entities. E.g. Creating category inserts a product and a subcategory.
		//Note: The above note is FALSE. Saving product without saving category results in:
		//java.lang.IllegalStateException: org.hibernate.**TransientPropertyValueException**: 
		// object references an unsaved transient instance - save the transient instance before flushing : 
		//edu.ds.ms.retail.catalog.entity.Product.category -> edu.ds.ms.retail.catalog.entity.Category
		
		//learning about persistence context
		//checkPersistenceContext(prod, cat, subCat);
		categoryService.createCategory(cat);
		//checkPersistenceContext(prod, cat, subCat);
		subCategoryService.createSubCategory(subCat);
		//checkPersistenceContext(prod, cat, subCat);
		productService.createProduct(prod);
		//checkPersistenceContext(prod, cat, subCat);

		//Note: getAll...() that calls findAll() will throw com.sun.jdi.InvocationException (you can see it in the List<> returned)
		// if not @Transactional
		
		//check products
		List<Product> products = productService.getAllProducts();
		//2 products, 1 from product.sql, another from productRepository.save(prod)
		assertEquals(2, products.size());
		
		List<Category> categories = categoryService.getAllCategories();
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		assertEquals(1, categories.size());
		assertEquals(1, subCategories.size());
	}
	
	@Test
	@Commit //to verify in database if the primary-foreign key relationships got created correctly
	//@Transactional code not commit the data. So, using @Commit to commit data and verify data.
	//Without @Transactional, there is no hibernate session (or no objects in the sessions persistence storage), so fetching data results in null pointer exception.
	void testCreateMultipleProductsOfNewCategoryAndSubcategory() {
		Category catElectronics = new Category(); //New Category: Electronics
		catElectronics.setName("Electronics");
		SubCategory subcatAudio = new SubCategory(); //New SubCategory: Audio
		subcatAudio.setName("Audio");
		SubCategory subcatVideo = new SubCategory(); //New SubCategory: Video
		subcatVideo.setName("Video");
		Product prodSpeaker = new Product(); //New Product: Speaker
		prodSpeaker.setName("Speaker");
		Product prodTV = new Product(); //New Product: Television
		prodTV.setName("Television");
		
		//many-to-one mappings
		catElectronics.addSubCategory(subcatAudio);
		catElectronics.addSubCategory(subcatVideo);
		catElectronics.addProduct(prodSpeaker);
		catElectronics.addProduct(prodTV);
		subcatAudio.addProduct(prodSpeaker);
		subcatVideo.addProduct(prodTV);
		
		//persist categories and products
		categoryService.createCategory(catElectronics);
		subCategoryService.createSubCategory(subcatAudio);
		subCategoryService.createSubCategory(subcatVideo);
		productService.createProduct(prodSpeaker);
		productService.createProduct(prodTV);
		
		//assert relationships
		Product fetchProd0 = productService.getProductByName("Speaker"); //.getAllProducts().get(1); //assuming Speaker
		Product fetchProd1 = productService.getProductByName("Television"); //getAllProducts().get(0); //assuming Television
		assertEquals(catElectronics.getName(), fetchProd0.getCategory().getName()); //Assert Electronics category
		assertEquals(catElectronics.getName(), fetchProd1.getCategory().getName()); //Assert Electronics category
		assertEquals(subcatAudio.getName(), fetchProd0.getSubCategory().getName()); //Assert Audio subcategory
		assertEquals(subcatVideo.getName(), fetchProd1.getSubCategory().getName()); //Assert Video subcategory
	}

	@Test
	@Commit
	void testCreateProductOfExistingCategoryAndSubCategory() {
		Category catElectronics = categoryService.getCategoryByName("Electronics"); //Existing Category: Electronics
		SubCategory subcatAudio = subCategoryService.getSubCategoryByName("Audio"); //Existing SubCategory: Audio
		Product prodSpeaker = new Product(); //New Product: Bose Speaker
		prodSpeaker.setName("Bose Speaker");
		
		//many-to-one mappings
		catElectronics.addProduct(prodSpeaker);
		subcatAudio.addProduct(prodSpeaker);
		
		//persist product
		productService.createProduct(prodSpeaker);
	}
	
	@Test
	@Commit
	void testCreateProductOfExistingCategoryAndNewSubCategory() {
		Category catElectronics = categoryService.getCategoryByName("Electronics"); //Existing Category: Electronics
		SubCategory subcatHomeAppliance = new SubCategory(); //New SubCategory: Home Appliance
		subcatHomeAppliance.setName("Home Appliance");
		Product prodSpeaker = new Product(); //New Product: Washing Machine
		prodSpeaker.setName("Washing Machine");
		
		//many-to-one mappings
		catElectronics.addSubCategory(subcatHomeAppliance);
		catElectronics.addProduct(prodSpeaker);
		subcatHomeAppliance.addProduct(prodSpeaker);
		
		//persist product
		//categoryService.createCategory(catElectronics); //this creates duplicate products and subcategories
		subCategoryService.createSubCategory(subcatHomeAppliance);
		productService.createProduct(prodSpeaker);
	}
	
	@Test
	void testGetProductByCategoryName() {
		Product prodTelevision = productService.getProductByCategoryName("Electronics");
		int a = 0;
	}
	
	@Test
	void testGetSubCategoryByNameAndCategoryName() {
		SubCategory subCategory = subCategoryService.getSubCategoryByNameAndCategoryName("Curtains", "Home Furnishing");
		String categoryName = subCategory.getCategory().getName();
		int a = 0;
	}
	
	@Test
	void testGetProductsByCategoryNameAndSubCategoryName() {
		List<Product> products = productService.getProductsByCategoryNameAndSubCategoryName("Electronics", "Audio");
		int a = 0;
	}
	
	@Test
	void testGetProductsByCategoryDescription() {
		List<Product> products = productService.getProductsByDescription("sound");
		int a = 0;
	}
}
