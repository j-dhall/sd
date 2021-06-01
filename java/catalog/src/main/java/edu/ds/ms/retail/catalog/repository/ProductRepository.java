package edu.ds.ms.retail.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ds.ms.retail.catalog.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public Optional<Product> findByName(String name);
}
