package com.sd.spring.boot.mvc.demo;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super();
    }

    public BookNotFoundException(final String message) {
        super(message);
    }
}
