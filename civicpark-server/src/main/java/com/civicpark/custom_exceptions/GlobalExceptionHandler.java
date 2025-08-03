package com.civicpark.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// ---------- Resource Not Found Exception -----------//
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND); // create a response entity with status code only
	}
	
	

}
