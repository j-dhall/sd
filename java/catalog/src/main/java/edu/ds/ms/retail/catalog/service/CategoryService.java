package edu.ds.ms.retail.catalog.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	EntityManager entityManager;
	@Autowired
	CategoryRepository categoryRepository;
	
	//CrudRepository:save()
	//throws IllegalArgumentException - in case the given entity is null.
	//returns the saved entity with id created by the database; will never be null.
	//so, no need to use Optional.of()
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name).orElse(null); //TODO: Throw exception
	}
}
