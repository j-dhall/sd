package com.sd.spring.boot.mvc.demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.printf("REMOTE HOST: %s\n", req.getRemoteHost());
		System.out.printf("REMOTE ADDRESS: %s\n", req.getRemoteAddr());
		filterChain.doFilter(req, resp);
	}

}
