package edu.ds.ms.retail.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ds.ms.retail.catalog.entity.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
	public Optional<SubCategory> findByName(String name);
	public Optional<SubCategory> findByNameAndCategoryName(String name, String categoryName); //order of arguments is important. argument name can be anything.
}
