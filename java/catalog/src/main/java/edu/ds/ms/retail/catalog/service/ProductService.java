package edu.ds.ms.retail.catalog.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ds.ms.retail.catalog.entity.Product;
import edu.ds.ms.retail.catalog.entity.SubCategory;
import edu.ds.ms.retail.catalog.repository.ProductRepository;
import edu.ds.ms.retail.catalog.repository.ProductsSpecifications;
import edu.ds.ms.retail.catalog.repository.SubCategoryRepository;

@Service
public class ProductService {
	@Autowired
	EntityManager entityManager;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	//EntityGraph for named query "product-graph-subcategory-and-category-subcategories"
	private Product getEntityGraph(Product product) {
		EntityGraph entityGraph = entityManager.getEntityGraph("product-graph-subcategory-and-category-subcategories");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.where(criteriaBuilder.equal(root.<Integer>get("id"), product.getId()));
		TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setHint("javax.persistence.loadgraph", entityGraph);
		Product result = typedQuery.getSingleResult();
		return result;
	}
	
	public Product saveProduct(Product product) {
		/*
		//BROKEN CODE
		//Reason: Bad design: Separate tables for Category and SubCategory, instead of a single table with parentCategoryId column.
		//Broken: A product's category can be changed to a category which is not the same category of the products subcategory.
		//Broken: lot of custom code to check integrity constraints.

		//need to check in various places in the logic
		//if the category/subcategory exist in the persistence store
		PersistenceUnitUtil unitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		boolean bCategoryLoaded = unitUtil.isLoaded(product, "category");
		boolean bSubCategoryLoaded = unitUtil.isLoaded(product, "subCategory");

		
		
		//Since we have separate category and subcategory tables,
		//instead of a single category table with parent relationship,
		//we have 2 foreign keys in product that individually verify integrity constraints,

		//So, it is possible to create a product with a subcategory, but not category.
		//Though subcategory has a relationship with category via foreign key in subcategory table, 
		//that relaltionship is now not present in the product table.
		//This requirement of duplication of relationship is due to poor design of
		//category and subcategory being in separate tables
		//instead of being in a single table with parent category field.
		
		//So, to validate the constraint of category always being set when subcategory is set,
		//we introduce the following custom validation logic.
		if(product.getSubCategory() != null) {
			if(product.getCategory() == null) {
				//throw exception
				String error = null;
				if(bSubCategoryLoaded) {
					error = String.format("Category of sub-category %s missing for product %s.", product.getSubCategory().getName(), product.getName());
				} else {
					error = String.format("Category missing for product %s.", product.getName());
				}
				DataIntegrityViolationException e = new DataIntegrityViolationException(error);
				throw e;
			}
		}

		//Since we have separate category and subcategory tables,
		//instead of a single category table with parent relationship,
		//we have 2 foreign keys in product that individually verify integrity constraints,

		//So, it is possible for a product to have a subcategory
		//that is actually not a subcategory of the product's category.
		//say, category=Electronics, subcategory=Mathematics.
		//Mathematics belongs to the Books category, 
		//and subcategories of Electronics are Televisions and Speakers.
		
		//So, we validate that constraint in the following custom logic
		
		//Succeeds even for
		//CreateEntityIntegrationTest.testCreateProductWithExistingCategoryAndNewSubCategory()
		
		//We validate that constraint only if category and subcategory are not null.
		//null categories and subcategories for newly created product are considered 'loaded' by PersistenceUnitUtil.
		//So, we filter out null categories/subcategories before we proceed to use PersistenceUnitUtil. 
		if(product.getCategory() != null && product.getSubCategory() != null) {
			//This below condition being true means cases where categories are subcategories
			//are either created and assigned, or
			//fetched and assigned.
			//We, thus, have a case for validating the above mentioned constraint.
//			PersistenceUnitUtil unitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
//			boolean bCategoryLoaded = unitUtil.isLoaded(product, "category");
//			boolean bSubCategoryLoaded = unitUtil.isLoaded(product, "subCategory");

			//BROKEN
			//Loads product's original category, and not the category set by the test case.
			//Refer UpdateEntityIntegrationTest.testUpdateProductWithInvalidCategory()
			
			Product productGraph = getEntityGraph(product);
			
			//If product.subcategory is not null, and does not exist in persistent store,
			//it means, product was fetched without fetching its subcategory (due to lazy fetch).
			//And, it means that the subcategory was not overwritten.
			//We have already fetched the entity graph for the product.
			//So, we can set the subcategory from there.

			//BUG: What if the subcategory was overwritten by another (unfetched lazy) subcategory (from a different category)?
			//All this goes back to poor design of separate category and subcategory tables
			//instead of a single category table with parent category column.

			if(!bSubCategoryLoaded) {
				product.setSubCategory(productGraph.getSubCategory());
			}
			
			
			//if (bCategoryLoaded && bSubCategoryLoaded) {
				//if(product.getSubCategory() != null) {

					Set<SubCategory> subCategories = productGraph.getCategory().getSubCategories();//get subcategories of product category
							//subCategoryRepository.findByCategoryName(product.getCategory().getName());//get subcategories of product category
					boolean foundMatch = false;
					for(SubCategory subcat: subCategories) {//check if product subcategory is one among subcategories of product category
						if(subcat.getName().equalsIgnoreCase(product.getSubCategory().getName())) {
							foundMatch = true;
							break;
						}
					}
					if(!foundMatch) {
						//throw exception
						String error = String.format("Sub-category %s does not belong to category %s.", product.getSubCategory().getName(), productGraph.getCategory().getName());
						DataIntegrityViolationException e = new DataIntegrityViolationException(error);
						throw e;
					}
				//}
			//}
		}
		*/

		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	//We can have multiple products for a name.
	//So, use the plural form getProductsByName() below.
	/*
	public Product getProductByName(String name) {
		return productRepository.findByName(name).orElse(null); //TODO: Throw exception
	}
	*/
	
	public List<Product> getProductsByName(String name) {
		List<String> names = new ArrayList<String>();
		names.add(name);
		return productRepository.findByNameIn(names);
		//return productRepository.findByName(name).orElse(null); //TODO: Throw exception
	}

	//We can have multiple products for a name.
	//So, use the plural form getProductsByName() below.
	/*
	public Product getProductByCategoryName(String catName) {
		return productRepository.findByCategoryName(catName).iterator().next(); //TODO: Throw exception
	}
	*/
	
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
		return productRepository.findByCategoryNameInAndSubCategoryNameIn(catNames, subcatNames);
	}
	
	public List<Product> getProductsByDescription(String keyword) {
		Collection<Product> products = productRepository.findByDescriptionIgnoreCaseContaining(keyword);
		return new ArrayList<Product>(products);
	}
	
	public List<Product> getProductsByText(String text) {
		return productRepository.findAll(ProductsSpecifications.containsTextInAttributes(text));
	}
}
