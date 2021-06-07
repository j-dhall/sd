package edu.ds.ms.retail.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;

//import javax.persistence.EntityManager;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
//import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
public class CreateEntityIntegrationTest {
	//@Autowired
	//EntityManager entityManager;
	//@Autowired
	//SessionFactory sessionFactory;
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
	//@Transactional //TODO
	void testCreateCategory() {
		Category cat = new Category();//New Category
		cat.setName("Home Appliances");
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");
		
		//Save Category
		Category catSaved = categoryService.saveCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
	}
	
	//Test: Try creating Category with missing mandatory name
	//Throws: PropertyValueException
	//nested exception is org.hibernate.PropertyValueException: not-null property references a 
	// null or transient value : edu.ds.ms.retail.catalog.entity.Category.name
	@Test
	//Throws: org.hibernate.PropertyValueException
	//@Transactional //TODO
	void testCreateCategoryWithoutName() {
		Category cat = new Category();
		//cat.setName("Home Appliances");//omitting setting of mandatory field
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");
		try {
			categoryService.saveCategory(cat);//passing Category without mandatory name
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getCause() instanceof PropertyValueException);
			assertTrue(((PropertyValueException)e.getCause()).getEntityName().contains(Category.class.getName()));
			assertEquals(((PropertyValueException)e.getCause()).getPropertyName(), "name");
		}
	}

	//Test: Try creating null category
	//Throws: IllegalArgumentException
	//CrudRepository:save()
	//throws IllegalArgumentException - in case the given entity is null.
	@Test
	//@Transactional //TODO
	void testCreateCategoryFromNull() {
		try {
			categoryService.saveCategory(null);//passing invalid Category
		} catch (Exception e) {
			assertTrue(e instanceof InvalidDataAccessApiUsageException);
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}
	
	//Test: Try creating duplicate Category (category name should be unique)
	//Throws: java.sql.SQLIntegrityConstraintViolationException
	@Test
	//Throws: java.sql.SQLIntegrityConstraintViolationException
	//@Sql({"/test_data.sql"}) //data creation for test case
	//@Transactional
	void testCreateCategoryWithDuplicateName() {
		Category cat = new Category();//New Category
		String catName = "Electronics"; 
		cat.setName(catName);
		try {
			categoryService.saveCategory(cat);//passing Category with name of an existing category
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getCause() instanceof ConstraintViolationException);
			assertTrue(e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException);
			//String a = e.getCause().getCause().getMessage();
			//String b = String.format("Duplicate entry '%s'", catName);
			assertTrue(e.getCause().getCause().getMessage().startsWith(String.format("Duplicate entry '%s'", catName)));
		}
	}
	
	//CREATE SUBCATEGORY
	
	//Test: Create SubCategory of a new Category
	@Test
	//@Transactional //TODO
	void testCreateSubCategoryOfNewCategory() {
		Category cat = new Category();//New Category
		cat.setName("Home Appliances");
		cat.setDescription("Electricals used at home, like Washing Machine, Microwave Oven, etc");
		
		SubCategory subcat = new SubCategory();//New SubCategory
		subcat.setName("Kitchen");
		subcat.setDescription("Kitchen appliances like Microwave, Dishwasher, Chimney, Hob, etc");
		
		cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		
		//Save Category
		Category catSaved = categoryService.saveCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
		
		//Save SubCategory
		SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(catSaved.getId() == subcatSaved.getCategory().getId());//verify foreign-key
	}
	
	//Test: Create SubCategory of an existing Category
	@Test
	//@Sql({"/test_data.sql"}) //data creation for test case
	//@Transactional //- have fetched category in persistence context to use it later to add subcategory to it
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
		SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(cat.getId() == subcatSaved.getCategory().getId());//verify foreign-key
	}
	
	//Test: Create SubCategory without name
	//Throws: org.hibernate.PropertyValueException
	@Test
	void testCreateSubCategoryOfExistingCategoryWithoutSubCategoryName() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		assertNotNull(cat);
		assertTrue(cat.getId() > 0);
		
		SubCategory subcat = new SubCategory();//New SubCategory
		//subcat.setName("History");//subcategory without name
		subcat.setDescription("Books on world wars, and other important historical conflicts");

		//cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		subcat.setCategory(cat);
		
		//Save SubCategory
		try {
			SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getCause() instanceof PropertyValueException);
			assertTrue(((PropertyValueException)e.getCause()).getEntityName().contains(SubCategory.class.getName()));
			assertEquals(((PropertyValueException)e.getCause()).getPropertyName(), "name");
		}
	}
	
	//Test: Create SubCategory without Category
	//Throws: org.hibernate.PropertyValueException
	@Test
	void testCreateSubCategoryWithoutCategory() {
		SubCategory subcat = new SubCategory();//New SubCategory
		subcat.setName("Geography");
		subcat.setDescription("Books on continents, countries, flora and fauna, climate, wildlife, oceans, natural disasters, etc");
		
		//Save SubCategory
		try {
			SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getCause() instanceof PropertyValueException);
			assertTrue(((PropertyValueException)e.getCause()).getEntityName().contains(SubCategory.class.getName()));
			assertEquals(((PropertyValueException)e.getCause()).getPropertyName(), "category");
		}
	}
	
	//Test: Create SubCategory with duplicate name within the same Category
	//Throws: java.sql.SQLIntegrityConstraintViolationException
	@Test
	void testCreateSubCategoryWithDuplicateNameWithinSameCategory() {
		Category cat = categoryService.getCategoryByName("Books");//Existing Category
		assertNotNull(cat);
		assertTrue(cat.getId() > 0);
		
		SubCategory subcat = new SubCategory();//New SubCategory
		String subcatName = "Literature";
		subcat.setName(subcatName);//existing subcategory
		subcat.setDescription("Duplicate subcategory. Books on literature.");

		//cat.addSubCategory(subcat);//Many-to-One Category to SubCategory
		subcat.setCategory(cat);
		
		//Save SubCategory
		try {
			SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getCause() instanceof ConstraintViolationException);
			assertTrue(e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException);
			assertTrue(e.getCause().getCause().getMessage().startsWith("Duplicate entry"));
			assertTrue(e.getCause().getCause().getMessage().contains(subcatName));
		}
	}
	
	//CREATE PRODUCT
	
	//Test: Create Product
	@Test
	void testCreateProduct() {
		Product prod = new Product();//New Product
		String prodName = "Washing Machine"; 
		prod.setName(prodName);

		//Save Product
		Product prodSaved = productService.saveProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(prodSaved.getId() > 0);
		assertEquals(prodSaved.getName(), prodName);
	}
	
	//Test: Create Product without name
	//Throws: org.hibernate.PropertyValueException
	@Test
	void testCreateProductWithoutName() {
		Product prod = new Product();//New Product
		//String prodName = "Washing Machine";
		//prod.setName(prodName);

		//Save Product
		try {
			Product prodSaved = productService.saveProduct(prod);
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
			assertTrue(e.getCause() instanceof PropertyValueException);
			assertTrue(((PropertyValueException)e.getCause()).getEntityName().contains(Product.class.getName()));
			assertEquals(((PropertyValueException)e.getCause()).getPropertyName(), "name");
		}
	}
	
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
		Category catSaved = categoryService.saveCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
		
		//Save Product
		Product prodSaved = productService.saveProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(catSaved.getId() == prodSaved.getCategory().getId());//verify foreign-key
	}
	
	//Test: Create Product with new SubCategory
	@Test
	void testCreateProductWithoutCategoryWithNewSubCategory() {
		SubCategory subcat = subCategoryService.getSubCategoryByCategoryNameAndSubCategoryName("Electronics", "Speakers"); //Existing SubCategory

		Product prod = new Product();//New Product
		prod.setName("Denon Speakers");
		
		//subcat.addProduct(prod);//Many-to-One Category to Product
		prod.setSubCategory(subcat);
				
		//Save Product
		try {
			Product prodSaved = productService.saveProduct(prod);
		} catch (Exception e) {
			assertTrue(e instanceof DataIntegrityViolationException);
		}
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
		Category catSaved = categoryService.saveCategory(cat);
		assertNotNull(catSaved);
		assertTrue(catSaved.getId() > 0);
		
		//Save SubCategory
		SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(catSaved.getId() == subcatSaved.getCategory().getId());//verify foreign-key
		
		//Save Product
		Product prodSaved = productService.saveProduct(prod);
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
		Product prodSaved = productService.saveProduct(prod);
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
		SubCategory subcatSaved = subCategoryService.saveSubCategory(subcat);
		assertNotNull(subcatSaved);
		assertTrue(subcatSaved.getId() > 0);
		assertTrue(cat.getId() == subcatSaved.getCategory().getId());//verify foreign-key

		//Save Product
		Product prodSaved = productService.saveProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(cat.getId() == prodSaved.getCategory().getId());//verify foreign-key
		assertTrue(subcatSaved.getId() == prodSaved.getSubCategory().getId());//verify foreign-key
	}
	
	/*
	private void checkPersistenceContext(Product prod, Category cat, SubCategory subCat) {
		boolean contains = entityManager.contains(prod);
		contains = entityManager.contains(cat);
		contains = entityManager.contains(subCat);
		contains = false; //dummy for breakpoint
	}
	*/
	
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

		SubCategory subcat = subCategoryService.getSubCategoryByCategoryNameAndSubCategoryName("Books", "Mathematics");//Existing SubCategory
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
		Product prodSaved = productService.saveProduct(prod);
		assertNotNull(prodSaved);
		assertTrue(cat.getId() == prodSaved.getCategory().getId());//verify foreign-key
		assertTrue(subcat.getId() == prodSaved.getSubCategory().getId());//verify foreign-key
		
		//sessionFactory.getCurrentSession().getTransaction().commit();
	}

}
