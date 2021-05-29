package edu.ds.ms.retail.catalog.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	EntityManager entityManager;
	@Autowired
	ProductRepository productRepository;
	
	public Optional<Product> createProduct(Product product) {
		return Optional.of(productRepository.save(product));
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
}
