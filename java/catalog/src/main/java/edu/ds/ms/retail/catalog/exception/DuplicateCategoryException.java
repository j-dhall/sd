package edu.ds.ms.retail.catalog.exception;

public class DuplicateCategoryException extends RuntimeException {
	public DuplicateCategoryException(String catName) {
		super(String.format("A category by the name %s already exists.", catName));
	}
}
