package edu.ds.ms.retail.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoriesController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping(value = "/category")
	public List<Category> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories(); 
		return categories;
	}
}
