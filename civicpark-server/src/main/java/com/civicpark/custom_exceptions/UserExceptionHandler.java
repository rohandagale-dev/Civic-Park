package com.civicpark.custom_exceptions;

@SuppressWarnings("serial")
public class UserExceptionHandler extends RuntimeException {
	public UserExceptionHandler(String message) {
		super(message);

	}
}
