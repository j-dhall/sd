package com.sd.spring.depinject;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationContextTestResourceNameType.class})
class FieldResourceInjectionIntegrationTest {

	//https://www.baeldung.com/spring-annotations-resource-inject-autowire
	//Demo: Resource type dependency injection (DI)
	//Other DI methods are Inject and Autowired
	//Resource DI applicable for field and setter
	//Demo for setter
	
	@Resource(name="namedFile")
	private File defaultFile; //field
	
	@Test
	void test() {
		assertNotNull(defaultFile);
		assertEquals("namedFile.txt", defaultFile.getName());
	}

}
