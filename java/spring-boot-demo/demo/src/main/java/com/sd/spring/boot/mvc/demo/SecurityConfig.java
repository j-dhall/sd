package com.sd.spring.boot.mvc.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Allow unrestricted access
		//CSRF: Cross-Site Request Forgery CSRF attacks
		http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
	}

}
