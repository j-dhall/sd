package edu.ds.ms.retail.catalog.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import edu.ds.ms.retail.catalog.entity.Product;

//https://docs.spring.io/spring-data/jpa/docs/1.7.0.RELEASE/reference/html/#specifications
//https://stackoverflow.com/questions/25872637/spring-data-multi-column-searches
public class ProductsSpecifications {
	public static Specification<Product> containsTextInAttributes(String text) {
		if (!text.contains("%")) {
			text = "%" + text + "%";
		}
		final String finalText = text;
		
		return new Specification<Product> () {

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				//return criteriaBuilder.or(null);
				return criteriaBuilder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a-> {
                    if (a.getJavaType().getSimpleName().equalsIgnoreCase("string")) {
                        return true;
                    }
                    else {
                        return false;
                }}).map(a -> criteriaBuilder.like(root.get(a.getName()), finalText)
                    ).toArray(Predicate[]::new)
                );
				
			}
			
		};
	}
}
