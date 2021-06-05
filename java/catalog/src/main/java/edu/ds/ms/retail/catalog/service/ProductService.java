package edu.ds.ms.retail.catalog.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.repository.ProductRepository;
import edu.ds.ms.retail.catalog.repository.ProductsSpecifications;

@Service
public class ProductService {
	@Autowired
	EntityManager entityManager;
	@Autowired
	ProductRepository productRepository;
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductByName(String name) {
		return productRepository.findByName(name).orElse(null); //TODO: Throw exception
	}
	
	public List<Product> getProductsByName(String name) {
		List<String> names = new ArrayList<String>();
		names.add(name);
		return productRepository.findByNameIn(names);
		//return productRepository.findByName(name).orElse(null); //TODO: Throw exception
	}
	
	public Product getProductByCategoryName(String catName) {
		return productRepository.findByCategoryName(catName).iterator().next(); //TODO: Throw exception
	}
	
	public List<Product> getProductsByCategoryName(String catName) {
		List<String> catNames = new ArrayList<String>();
		catNames.add(catName);
		return productRepository.findByCategoryNameIn(catNames);
	}
	
	public List<Product> getProductsByCategoryNameAndSubCategoryName(String catName, String subcatName) {
		List<String> catNames = new ArrayList<String>();
		catNames.add(catName);
		List<String> subcatNames = new ArrayList<String>();
		subcatNames.add(subcatName);
		//Collection<Product> products = productRepository.findByCategoryNameAndSubCategoryName(catName, subcatName);
		return productRepository.findByCategoryNameInAndSubCategoryNameIn(catNames, subcatNames);
		//return new ArrayList<Product>(products);
	}
	
	public List<Product> getProductsByDescription(String keyword) {
		Collection<Product> products = productRepository.findByDescriptionIgnoreCaseContaining(keyword);
		return new ArrayList<Product>(products);
	}
	
	public List<Product> getProductsByText(String text) {
		return productRepository.findAll(ProductsSpecifications.containsTextInAttributes(text));
	}
}
