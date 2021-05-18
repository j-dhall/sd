package com.sd.spring.boot.mvc.demo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //NOTE: This tag is needed for the listener to be available in the application context.
@WebListener
public class SimpleListener implements ServletContextListener {

	Logger logger = LoggerFactory.getLogger(SimpleListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("SIMPLE LISTENER DESTROYED\n");
		ServletContextListener.super.contextDestroyed(sce);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("SIMPLE LISTENER INITIALIZED\n");
		ServletContextListener.super.contextInitialized(sce);
	}

}
