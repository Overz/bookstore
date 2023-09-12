package com.example.bookstore.auth.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenError extends RuntimeException {
	private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

	public ForbiddenError() {
		this("Forbidden!");
	}

	public ForbiddenError(String message) {
		super(message);
	}

	public ForbiddenError(String message, Throwable cause) {
		super(message, cause);
	}
}
