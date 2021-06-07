package edu.ds.ms.retail.catalog.exception;

public class DuplicateSubCategoryException extends RuntimeException {
	public DuplicateSubCategoryException(String catName, String subcatName) {
		super(String.format("A subcategory by the name %s already exists under category %s.", subcatName, catName));
	}
}
