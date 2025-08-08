package com.civicpark.custom_exceptions;

public class BadCredentialsException extends RuntimeException {
	public BadCredentialsException(String message) {
		super(message);
	}
}
