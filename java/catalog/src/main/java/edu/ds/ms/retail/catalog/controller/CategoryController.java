package edu.ds.ms.retail.catalog.controller;

import java.util.ArrayList;
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
	public List<Category> /*String*/ getAllCategories() {
		log.debug("INSIDE CategoryController.getAllCategories().");
		List<Category> categories = categoryService.getAllCategories();
		
		//HTTP 500: Internal Server Error
		//if we do not set the lazy-fetched persistence store members to null.
		//I think this is related to json marshalling.
		for(Category cat: categories) {
			cat.setSubCategories(null);
			cat.setProducts(null);
		}

		return categories;
	}
}
