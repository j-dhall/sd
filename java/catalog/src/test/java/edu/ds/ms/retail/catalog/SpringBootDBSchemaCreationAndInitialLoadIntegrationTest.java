package edu.ds.ms.retail.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.repository.ProductRepository;
import edu.ds.ms.retail.catalog.service.ProductService;

@SpringBootTest
class SpringBootDBSchemaCreationAndInitialLoadIntegrationTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	//test the repository layer. intention was to test integrity constraints
	@Test
	@Sql({"/product.sql"})
	void testUsingRepository() {
		Product prod = new Product();
		prod.setName("Echo");
		prod.setDescription("Amazon Echo");
		try {
			productRepository.save(prod);
			//2 products, 1 from product.sql, another from productRepository.save(prod) 
			assertEquals(2, productRepository.findAll().size());
		} catch (Exception e) { //Not useful, see note below
			//NOTE: tried an SQL with foreign key constraints violated, but could not catch SQLIntegrityConstraintViolationException here.
			//The test case fails and SQLIntegrityConstraintViolationException shows up in the log.
			//This is probably due to a repository weapper.
			//Read here: https://stackoverflow.com/questions/54876448/how-to-catch-hibernate-constraintviolationexception-or-spring-dataintegrityviol
			assertThat(e).isInstanceOf(SQLIntegrityConstraintViolationException.class)
			.hasMessage("Cannot add or update a child row: a foreign key constraint fails");
		}	
	}
	
	
	//test the service layer. intention was to test integrity constraints
	@Test
	@Sql({"/product.sql"})
	void testUsingService() {
		Product prod = new Product();
		prod.setName("Echo");
		prod.setDescription("Amazon Echo");
		try {
			productService.createProduct(prod);
			List<Product> products = productService.getAllProducts();
			//2 products, 1 from product.sql, another from productRepository.save(prod)
			assertEquals(2, products.size());
		} catch (Exception e) {
			assertThat(e).isInstanceOf(SQLIntegrityConstraintViolationException.class)
			.hasMessage("Cannot add or update a child row: a foreign key constraint fails");
		}
	}

}
