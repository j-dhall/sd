package com.sd.spring.depinject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@SpringBootTest //The @SpringBootTest annotation tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance) and use that to start a Spring application context.
//We do not have any class with @SpringBootApplication
//@Configuration
//@ComponentScan
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationContextTestConfiguration.class})
public class ApplicationContextTest {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void test() {
		assertNotNull(applicationContext.getBean(ComponentExample.class));
		assertNotNull(applicationContext.getBean(ControllerExample.class));
		assertNotNull(applicationContext.getBean(RepositoryExample.class));
		assertNotNull(applicationContext.getBean(ServiceExample.class));
		assertNotNull(applicationContext.getBean(CustomComponentExample.class));
	}
}
