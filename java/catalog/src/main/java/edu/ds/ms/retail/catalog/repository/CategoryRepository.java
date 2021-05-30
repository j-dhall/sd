package edu.ds.ms.retail.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ds.ms.retail.catalog.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
