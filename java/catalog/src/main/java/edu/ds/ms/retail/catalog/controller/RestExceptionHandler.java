package edu.ds.ms.retail.catalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.ds.ms.retail.catalog.exception.ErrorCodes;
import edu.ds.ms.retail.catalog.exception.ResponseStatusError;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
		ResponseStatusError error = new ResponseStatusError(ErrorCodes.INVALID_ARGUMENT.getCode(), ex.getMessage());
		return ResponseEntity.badRequest().body(null);
	}
}
