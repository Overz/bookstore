package com.bookstore.errors;

import lombok.Getter;

@Getter
public class NotFoundError extends RuntimeException {
	private final int status = 404;

	public NotFoundError() {
		this("Not Found!");
	}

	public NotFoundError(String message) {
		super(message);
	}
}
