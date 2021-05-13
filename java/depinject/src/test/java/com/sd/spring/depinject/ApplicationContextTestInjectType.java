package com.sd.spring.depinject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestInjectType {
	
	@Bean
	public ArbitraryDependency injectDependency() {
		ArbitraryDependency injectDependency = new ArbitraryDependency();
		return injectDependency;
	}
}
