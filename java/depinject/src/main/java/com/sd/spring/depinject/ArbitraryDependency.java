package com.sd.spring.depinject;

import org.springframework.stereotype.Component;

@Component
public class ArbitraryDependency {
	private static final String label = "Arbitrary Dependency";
	
	public String toString() {
		return label;
	}
}
