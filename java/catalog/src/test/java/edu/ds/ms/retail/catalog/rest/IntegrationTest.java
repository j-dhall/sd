package edu.ds.ms.retail.catalog.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import edu.ds.ms.retail.catalog.CatalogApplication;
import edu.ds.ms.retail.catalog.entity.Category;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CatalogApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestInstance(Lifecycle.PER_CLASS)
//@ContextConfiguration(classes = {RestTemplateAutoConfiguration.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test_data.sql"})
public class IntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	//private final static String URI = "/categories";
	
	//@Autowired
	//private static RestTemplateBuilder restTemplateBuilder;
	
	//@Autowired
	//RestTemplate restTemplate;
	
	//private static RestTemplate restTemplate;
	/*
	@BeforeAll
	public static void setup() {
		restTemplate = restTemplateBuilder.build();
	}*/
	
	@Test
	public void testGetCategories() {
		//String url = "http://localhost:8080/categories";
		//List<Category> categories = restTemplate.getForObject(url, (new ArrayList<Category>()).getClass());
		//int a = 0;
		String categories = restTemplate.getForObject
				("http://localhost:" + port + "/categories",
				String.class);
		int a = 0;
	}
	
	@Test
	public void testCreateCategory() {
		Category cat = new Category();
		cat.setName("Integration Tests");
		cat.setDescription("Testing creation of category using the REST interface.");
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity
				("http://localhost:" + port + "/categories", 
				cat, String.class);
		
		String catCreated =  responseEntity.getBody();
	}
}
