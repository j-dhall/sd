package com.sd.spring.boot.mvc.demo;

public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException() {
        super();
    }
	
    public BookIdMismatchException(final String message) {
        super(message);
    }
}
