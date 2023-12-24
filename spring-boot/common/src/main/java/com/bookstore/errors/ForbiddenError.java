package com.bookstore.errors;

import lombok.Getter;

@Getter
public class ForbiddenError extends RuntimeException {
	private final int status = 403;

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
