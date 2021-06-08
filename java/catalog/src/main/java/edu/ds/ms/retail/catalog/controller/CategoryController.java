package edu.ds.ms.retail.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping
	public List<Category> getAllCategories() {
		//logging demo using lombak slf4j (defaults to using Logback implementation)
		log.trace("A TRACE Message.");
		log.debug("A DEBUG Message.");
		log.info("A INFO Message.");
		log.warn("A WARN Message.");
		log.error("A ERROR Message.");
		
		List<Category> categories = categoryService.getAllCategories(); 
		return categories;
	}
}
