package com.sd.spring.depinject;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestResourceNameType {
	
	@Bean(name="namedFile")
	public File namedFile() {
		File namedFile = new File("namedFile.txt");
		return namedFile;
	}
}
