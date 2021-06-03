package edu.ds.ms.retail.catalog.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.repository.SubCategoryRepository;

@Service
public class SubCategoryService {
	@Autowired
	EntityManager entityManager;
	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	public SubCategory createSubCategory(SubCategory subCategory) {
		return subCategoryRepository.save(subCategory);
	}
	
	public List<SubCategory> getAllSubCategories () {
		return subCategoryRepository.findAll();
	}
	
	//subcategory should not be accessible without mentioning category
	//so, commenting out this method
	/*
	private SubCategory getSubCategoryByName(String name) {
		return subCategoryRepository.findByName(name).orElse(null); //TODO: Throw exception
	}*/
	
	public SubCategory getSubCategoryByNameAndCategoryName(String name, String categoryName) {
		return subCategoryRepository.findByNameAndCategoryName(name, categoryName).orElse(null);
	}
}
