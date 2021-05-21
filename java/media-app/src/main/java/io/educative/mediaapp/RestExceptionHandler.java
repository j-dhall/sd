package io.educative.mediaapp;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(PlaylistNotFoundException.class)
	public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
		ResponseStatusError error = new ResponseStatusError(ErrorCodes.PLAYLIST_NOT_FOUND.code(), ex.getMessage());
		return ResponseEntity.badRequest().body(error);		
	}
}
