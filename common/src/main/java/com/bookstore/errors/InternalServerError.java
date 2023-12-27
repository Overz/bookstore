package com.bookstore.errors;

import lombok.Getter;

@Getter
public class InternalServerError extends RuntimeException {
	private final int status = 500;

	public InternalServerError() {
		this("Internal Server Error!");
	}

	public InternalServerError(String message) {
		super(message);
	}

	public InternalServerError(String message, Throwable cause) {
		super(message, cause);
	}
}
