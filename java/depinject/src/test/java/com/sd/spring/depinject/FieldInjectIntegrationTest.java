package com.sd.spring.depinject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationContextTestInjectType.class})
public class FieldInjectIntegrationTest {
	
	//https://www.baeldung.com/spring-annotations-resource-inject-autowire
	//Demo: Inject type dependency injection (DI)
	//Other DI methods are Resource and Autowired
	//Inject DI applicable for field and setter
	//Demo for field, match by TYPE
	
	@Inject
	private ArbitraryDependency fieldInjectDependency;
	
	@Test
	void test() {
		assertNotNull(fieldInjectDependency);
		assertEquals("Arbitrary Dependency", fieldInjectDependency.toString());
	}
}
