package edu.ds.ms.retail.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.EntityManager;

import org.hibernate.PropertyValueException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.repository.CategoryRepository;
import edu.ds.ms.retail.catalog.service.CategoryService;
import edu.ds.ms.retail.catalog.service.ProductService;
import edu.ds.ms.retail.catalog.service.SubCategoryService;

@SpringBootTest
@ActiveProfiles("test")
//@Transactional
//DirtiesContext: The SQL script was running at the beginning of every test case, resulting in primary key violation at the first line (insert category)
//DirtiesContext: The data inserted inside a test case is cleaned up at the beginning of the next test case.
//DirtiesContext: https://stackoverflow.com/questions/29153100/sql-one-time-per-class
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test_data.sql"}) //data creation for test case
//@Sql(scripts = {"/test_data.sql"}, config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)) //data creation for test case
public class ServiceIntegrationTest {
	@Autowired
	EntityManager entityManager;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	CategoryService categoryService;
	@Autowired
	SubCategoryService subCategoryService;
	@Autowired
	ProductService productService;
	
//	@BeforeAll
//	@Sql({"/test_data.sql"})
//	public static void createTestData() {
//	}
	
	//CREATE CATEGORY
	
	//Test: Create Category
	//CrudRepository:save()
	//returns the saved entity with id created by the database; will never be null.
	@Test
	@Transactional //TODO
	void testCreateCategory() {
		Category cat = new Category();//New Category
		cat.setName("Home Appliances");
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");
		
		//Save Category
		Category catSaved = categoryService.createCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
	}
	
	//Test: Try creating Category with missing mandatory name
	//nested exception is org.hibernate.PropertyValueException: not-null property references a 
	// null or transient value : edu.ds.ms.retail.catalog.entity.Category.name
	@Test
	@Transactional //TODO
	void testCreateCategoryWithPropertyValueException() {
		Category cat = new Category();
		//cat.setName("Home Appliances");//omitting setting of mandatory field
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");
		try {
			categoryService.createCategory(cat);//passing Category without mandatory name
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof PropertyValueException);
		}
	}

	//Test: Try creating null category
	//CrudRepository:save()
	//throws IllegalArgumentException - in case the given entity is null.
	@Test
	@Transactional //TODO
	void testCreateCategoryWithIllegalArgumentException() {
		try {
			categoryService.createCategory(null);//passing invalid Category
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}
	
	//Test: Try creating duplicate Category (category name should be unique)
	//throws java.sql.SQLIntegrityConstraintViolationException
	@Test
	//@Sql({"/test_data.sql"}) //data creation for test case
	@Transactional
	void testCreateDuplicateCategoryWithSQLIntegrityConstraintViolationException() {
		Category cat = new Category();//New Category
		cat.setName("Electronics");
		try {
			categoryService.createCategory(cat);//passing Category with name of an existing category
		} catch (Exception e) {
			assertTrue(e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException);
		}
	}
	
	//CREATE SUBCATEGORY
	
	//Test: Create SubCategory of a new Category
	@Test
	@Transactional //TODO
	void testCreateSubCategoryOfNewCategory() {
		Category cat = new Category();//New Category
		cat.setName("Home Appliances");
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");
		
		SubCategory subcat = new SubCategory();//New SubCategory
		subcat.setName("Kitchen");
		subcat.setDescription("Kitchen appliances like Microwave, Dishwasher, Chimney, Hob, etc");
		
		cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		
		//Save Category
		Category catSaved = categoryService.createCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
		
		//Save SubCategory
		SubCategory subcatSaved = subCategoryService.createSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(catSaved.getId() == subcatSaved.getCategory().getId());//verify foreign-key
	}
	
	//Test: Create SubCategory of an existing Category
	@Test
	//@Sql({"/test_data.sql"}) //data creation for test case
	@Transactional //- have fetched category in persistence context to use it later to add subcategory to it
	//@Commit - check subcategory creation in database after the test finishes
	void testCreateSubCategoryOfExistingCategory() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		assertNotNull(cat);
		assertTrue(cat.getId() > 0);
		
		SubCategory subcat = new SubCategory();//New SubCategory
		subcat.setName("History");
		subcat.setDescription("Books on world wars, and other important historical conflicts");

		//cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		subcat.setCategory(cat);
		
		//Save SubCategory
		SubCategory subcatSaved = subCategoryService.createSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(cat.getId() == subcatSaved.getCategory().getId());//verify foreign-key
	}
	
	//CREATE PRODUCT
	
	//Test: Create Product with new Category
	@Test
	//@Transactional
	//@Commit
	void testCreateProductWithNewCategory() {
		Category cat = new Category(); //New Category
		cat.setName("Home Appliances");
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");

		Product prod = new Product();//New Product
		prod.setName("Washing Machine");
		
		cat.addProduct(prod);//Many-to-One Category to Product 
		
		//Save Category
		Category catSaved = categoryService.createCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
		
		//Save Product
		Product prodSaved = productService.createProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(catSaved.getId() == prodSaved.getCategory().getId());//verify foreign-key
	}
	
	//Test: Create Product with new Category and SubCategory
	@Test
	//@Transactional
	//@Commit
	void testCreateProductWithNewCategoryAndSubCategory() {
		Category cat = new Category(); //New Category
		cat.setName("Home Appliances");
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");

		SubCategory subcat = new SubCategory();//New SubCategory
		subcat.setName("Kitchen");
		subcat.setDescription("Kitchen appliances like Dishwasher, Microwave Oven, Toaster, Chimney, Hob, etc");

		Product prod = new Product();//New Product
		prod.setName("Dishwasher");
		
		cat.addProduct(prod);//Many-to-One Category to Product
		cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		subcat.addProduct(prod);//Many-to-One SubCategory to Product
		
		//Save Category
		Category catSaved = categoryService.createCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
		
		//Save SubCategory
		SubCategory subcatSaved = subCategoryService.createSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(catSaved.getId() == subcatSaved.getCategory().getId());//verify foreign-key
		
		//Save Product
		Product prodSaved = productService.createProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(catSaved.getId() == prodSaved.getCategory().getId());//verify foreign-key
		assertTrue(subcatSaved.getId() == prodSaved.getSubCategory().getId());//verify foreign-key
	}
	
	//Test: Create Product with existing Category
	@Test	
	//@Sql({"/test_data.sql"}) //data creation for test case
	//@Transactional
	//@Commit
	void testCreateProductWithExistingCategory() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		assertNotNull(cat);
		assertTrue(cat.getId() > 0);

		Product prod = new Product();//New Product
		prod.setName("Deep Learning - Yoshua Bengio");
		
		//cat.addProduct(prod);//Many-to-One Category to Product
		prod.setCategory(cat);

		//Save Product
		Product prodSaved = productService.createProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(cat.getId() == prodSaved.getCategory().getId());//verify foreign-key
	}
	
	//Test: Create Product with existing Category and new SubCategory
	@Test	
	//@Sql({"/test_data.sql"}) //data creation for test case
	//@Transactional
	//@Commit
	void testCreateProductWithExistingCategoryAndNewSubCategory() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		assertNotNull(cat);
		assertTrue(cat.getId() > 0);
		
		SubCategory subcat = new SubCategory();//New SubCategory
		subcat.setName("Machine Learning");
		subcat.setDescription("Topics in Machine Learning, Deep Learning, Reinforcement Learning, etc");

		Product prod = new Product();//New Product
		prod.setName("Deep Learning - Yoshua Bengio");
		
		//cat.addProduct(prod);//Many-to-One Category to Product
		//cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		prod.setCategory(cat);
		subcat.setCategory(cat);
		subcat.addProduct(prod);//Many-to-One SubCategory to Product

		//Save SubCategory
		SubCategory subcatSaved = subCategoryService.createSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(cat.getId() == subcatSaved.getCategory().getId());//verify foreign-key

		//Save Product
		Product prodSaved = productService.createProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(cat.getId() == prodSaved.getCategory().getId());//verify foreign-key
		assertTrue(subcatSaved.getId() == prodSaved.getSubCategory().getId());//verify foreign-key
	}
	
	
	private void checkPersistenceContext(Product prod, Category cat, SubCategory subCat) {
		boolean contains = entityManager.contains(prod);
		contains = entityManager.contains(cat);
		contains = entityManager.contains(subCat);
		contains = false; //dummy for breakpoint
	}
	
//	@BeforeEach
//	@Sql({"/test_data.sql"})
//	void createTestData() {
//	}
	//Test: Create Product with existing Category and existing SubCategory
	@Test
	//@Sql({"/test_data.sql"}) //data creation for test case
	//@Transactional
	//@Commit
	void testCreateProductWithExistingCategoryAndExistingSubCategory() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category

		//checkPersistenceContext(new Product(), cat, new SubCategory());
		
		assertNotNull(cat);
		assertTrue(cat.getId() > 0);

		SubCategory subcat = subCategoryService.getSubCategoryByNameAndCategoryName("Mathematics", "Books");//Existing SubCategory
		assertNotNull(subcat);
		assertTrue(subcat.getId() > 0);

		Product prod = new Product();//New Product
		prod.setName("Calculus - Tom Apostol");
		
		//cat.addProduct(prod);//Many-to-One Category to Product
		prod.setCategory(cat);
		
		//Redundant.
		//Even if we add subcategory to the category collection of subcategories, 
		//the collection being a set, and already having this subcategory,
		//the addition of subcategory to the set gets ignored.
		
		//cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		
		//subcat.addProduct(prod);//Many-to-One SubCategory to Product
		prod.setSubCategory(subcat);
		
		//Save Product
		Product prodSaved = productService.createProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(cat.getId() == prodSaved.getCategory().getId());//verify foreign-key
		assertTrue(subcat.getId() == prodSaved.getSubCategory().getId());//verify foreign-key
		
		//sessionFactory.getCurrentSession().getTransaction().commit();
	}
}
