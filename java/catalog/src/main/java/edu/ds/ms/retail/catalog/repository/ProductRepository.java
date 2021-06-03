package edu.ds.ms.retail.catalog.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ds.ms.retail.catalog.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public Optional<Product> findByName(String name);
	public Collection<Product> findByCategoryName(String catName);
	public Collection<Product> findByCategoryNameAndSubCategoryName(String catName, String subcatName);
	public Collection<Product> findByDescriptionIgnoreCaseContaining(String keyword);
	public List<Product> findAll(Specification<Product> spec); //use ProductSpecifications.containsTextInAttributes()
}
