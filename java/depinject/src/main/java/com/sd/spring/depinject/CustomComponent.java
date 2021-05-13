package com.sd.spring.depinject;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RUNTIME)
@Target(TYPE)
@Component //this is needed: https://www.baeldung.com/spring-component-annotation
//although it is not shown being used in this example: https://www.baeldung.com/java-custom-annotation
//Without @Component, the bean does not get created in the application context
public @interface CustomComponent {

}
