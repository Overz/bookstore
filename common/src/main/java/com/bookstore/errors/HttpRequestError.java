package com.bookstore.errors;

import lombok.Getter;

@Getter
public class HttpRequestError extends RuntimeException {
	private final int status;

	public HttpRequestError(int status, String message) {
		super(message);
		this.status = status;
	}
}
