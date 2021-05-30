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
	
	public Optional<SubCategory> createSubCategory(SubCategory subCategory) {
		return Optional.of(subCategoryRepository.save(subCategory));
	}
	
	public List<SubCategory> getAllSubCategories () {
		return subCategoryRepository.findAll();
	}
}
