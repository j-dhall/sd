package com.mkyong.listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//Caused by: java.lang.Error: factory already defined
//https://github.com/spring-projects/spring-boot/issues/21535
//https://github.com/spring-cloud/spring-cloud-netflix/blob/aee83fb1132f33ae4bb798536c6e0cabf368c880/spring-cloud-netflix-eureka-client/src/test/java/org/springframework/cloud/netflix/eureka/config/EurekaConfigServerBootstrapConfigurationTests.java#L90-L91
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;

public class MyAppServletContextListener 
               implements ServletContextListener{
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("######################ServletContextListener destroyed");
    }

        //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
		//Caused by: java.lang.Error: factory already defined
		//https://github.com/spring-projects/spring-boot/issues/21535
		TomcatURLStreamHandlerFactory.disable();
		
        System.out.println("#######################ServletContextListener started");	
    }
}