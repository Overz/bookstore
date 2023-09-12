package com.example.bookstore.auth.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerError extends RuntimeException {
	private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

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
