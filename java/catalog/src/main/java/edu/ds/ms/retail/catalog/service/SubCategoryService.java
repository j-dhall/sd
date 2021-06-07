package edu.ds.ms.retail.catalog.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.UniqueConstraint;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.exception.DuplicateSubCategoryException;
import edu.ds.ms.retail.catalog.repository.SubCategoryRepository;

@Service
public class SubCategoryService {
	@Autowired
	EntityManager entityManager;
	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	/*
	//check if name has changed
	//assumes this is a subcategory already existing in the database
	private boolean isNameChanged(SubCategory subCategory) {
		boolean nameChanged = false;
		//entityManager.detach(subCategory); //there is no transaction, and hence, no persistence context to detach from. so this step is redundant.
		SubCategory dbSubcat = subCategoryRepository.findById(subCategory.getId()).orElse(null);
		//if the name of the object that was in persistence context before detaching
		// is not equal to the name of the object got from the database
		if(!subCategory.getName().equals(dbSubcat.getName())) {
			nameChanged = true;
		}

		return nameChanged;
	}
	*/
	
	/*
	private boolean isNameDuplicate(SubCategory subCategory) {
		
		SubCategory subcatGraph = getEntityGraph(subCategory);
		
		boolean duplicateName = false;
		
		String catName = subCategory.getCategory().getName();//get name of category
		List<SubCategory> subCategories = subCategoryRepository.findByCategoryName(catName);//get subcategories of the category 
		//iterate over subcategories, checking for a match of name. if name matches, id should also match, else we have a name conflict
		for (SubCategory subcat: subCategories) {
			if(subCategory.getName().equalsIgnoreCase(subcat.getName())) {
				if(subCategory.getId() != subcat.getId()) {
					duplicateName = true;//a different subcategory with name same as this modified subcategory name was found
					break;
				}
			}
		}
		
		return duplicateName;
	}
	*/
	
	/*
	//EntityGraph for named query "subcategory-graph-with-category-subcategories"
	private SubCategory getEntityGraph(SubCategory subCategory) {
		EntityGraph entityGraph = entityManager.getEntityGraph("subcategory-graph-with-category-subcategories");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SubCategory> criteriaQuery = criteriaBuilder.createQuery(SubCategory.class);
		Root<SubCategory> root = criteriaQuery.from(SubCategory.class);
		criteriaQuery.where(criteriaBuilder.equal(root.<Integer>get("id"), subCategory.getId()));
		TypedQuery<SubCategory> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setHint("javax.persistence.loadgraph", entityGraph);
		SubCategory result = typedQuery.getSingleResult();
		return result;
	}
	*/
	
	public SubCategory saveSubCategory(SubCategory subCategory) {
		
		//We now have a unique constraint on a combination of 2 columns: (name, category)
		//by using uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "name"})
		//in the @Table annotation of the SubCategory entity.
		//So, we do not need custom logic to enforce that constraint.
		/*
		
		//unique constraint on subcategory name within category is not supported by the database schema.
		//so, we write custom logic here to enforce that constraint.
		
		//if this is an existing subcategory
		//we need to check that its name is unique within its category
		if(subCategory.getId() != null) {//if this is an existing subcategory
			//get entity graph of subcategory to get its category and all subcategories of that category
			SubCategory subCategoryGraph = getEntityGraph(subCategory);
			if(subCategory.getName() != subCategoryGraph.getName()) {//has the name of the subcategory changed? 
				for(SubCategory subcat: subCategoryGraph.getCategory().getSubCategories()) {
					//avoid comparing the updated subCategory with its DB persisted value
					//because, if the name was not changed, subCategory name will match with subcat, and that is OK
					if(subcat.getId() != subCategory.getId()) {
						if(subcat.getName().equalsIgnoreCase(subCategory.getName())) {//do we have a duplicate name?
							throw new DuplicateSubCategoryException(subCategoryGraph.getCategory().getName(), subCategory.getName());//TODO: throw an exception
						}//if(subcat.getName()
					}//if(subcat.getId()
				}//for(SubCategory subcat:
			}//if(subCategory.getName()
		}//if(subCategory.getId()
		
		*/
		
		//Exception caused by missing subcategory name
		//org.springframework.dao.DataIntegrityViolationException: 
		//not-null property references a null or transient value : 
		//edu.ds.ms.retail.catalog.entity.SubCategory.name; 
		//nested exception is org.hibernate.PropertyValueException: 
		//not-null property references a null or transient value : 
		//edu.ds.ms.retail.catalog.entity.SubCategory.name		

		//Exception caused by missing category name
		//org.springframework.dao.DataIntegrityViolationException: 
		//not-null property references a null or transient value : 
		//edu.ds.ms.retail.catalog.entity.SubCategory.category; 
		//nested exception is org.hibernate.PropertyValueException: 
		//not-null property references a null or transient value : 
		//edu.ds.ms.retail.catalog.entity.SubCategory.category
		
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
	
	public List<SubCategory> getSubCategoriesByCategoryName(String categoryName) {
		return subCategoryRepository.findByCategoryName(categoryName);
	}
	
	public SubCategory getSubCategoryByCategoryNameAndSubCategoryName(String categoryName, String subCategoryName) {
		return subCategoryRepository.findByCategoryNameAndName(categoryName, subCategoryName).orElse(null);
	}
}
