package com.example.bookstore.auth.errors;

public class BadRequestError extends RuntimeException {

	public BadRequestError(String message) {
		super(message);
	}
}
