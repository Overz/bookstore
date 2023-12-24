package com.bookstore.errors;

public class BadRequestError extends RuntimeException {

	public BadRequestError(String message) {
		super(message);
	}
}
