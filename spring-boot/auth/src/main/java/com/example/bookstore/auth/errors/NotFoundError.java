package com.example.bookstore.auth.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundError extends RuntimeException {
	private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

	public NotFoundError() {
		this("Not Found!");
	}

	public NotFoundError(String message) {
		super(message);
	}
}
