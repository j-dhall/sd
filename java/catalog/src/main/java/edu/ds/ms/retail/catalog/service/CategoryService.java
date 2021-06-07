package edu.ds.ms.retail.catalog.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.exception.DuplicateCategoryException;
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
	public Category saveCategory(Category category) {
		//Exception caused by missing category name
		//org.springframework.dao.DataIntegrityViolationException: 
		//not-null property references a null or transient value : 
		//edu.ds.ms.retail.catalog.entity.Category.name; 
		//nested exception is org.hibernate.PropertyValueException: 
		//not-null property references a null or transient value : 
		//edu.ds.ms.retail.catalog.entity.Category.name
		
		//Exception caused by duplicate category name
		//org.springframework.dao.DataIntegrityViolationException: 
		//could not execute statement; SQL [n/a]; constraint [category.UK_46ccwnsi9409t36lurvtyljak]; 
		//nested exception is org.hibernate.exception.ConstraintViolationException: 
		//could not execute statement
		
		Category catSaved = null;
		//try {
			catSaved = categoryRepository.save(category);
//		} catch (DataIntegrityViolationException e) {
//			DuplicateCategoryException catEx = new DuplicateCategoryException(category.getName());
//			catEx.initCause(e);//set inner exception to spring exception
//			throw(catEx);
//		}
		return catSaved;
	}
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name).orElse(null); //TODO: Throw exception
	}
}
