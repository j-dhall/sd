package com.sd.spring.depinject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//https://www.baeldung.com/spring-component-scanning
//to have component scan begin in this class and scan all classes in this package

@Configuration
@ComponentScan
public class ApplicationContextTestConfiguration {

}
