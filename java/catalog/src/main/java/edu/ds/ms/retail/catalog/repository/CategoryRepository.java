package edu.ds.ms.retail.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ds.ms.retail.catalog.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public Optional<Category> findByName(String name);
}
