package edu.ds.ms.retail.catalog.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.ds.ms.retail.catalog.exception.ErrorCodes;
import edu.ds.ms.retail.catalog.exception.ResponseErrorStatusAndMessage;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
		ResponseErrorStatusAndMessage error = new ResponseErrorStatusAndMessage(ErrorCodes.INVALID_ARGUMENT.getCode(), e.getCause().getCause().getMessage());
		return ResponseEntity.badRequest().body(error);
	}
}
