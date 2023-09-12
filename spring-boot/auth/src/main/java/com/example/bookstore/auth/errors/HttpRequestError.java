package com.example.bookstore.auth.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpRequestError extends RuntimeException {
	private final HttpStatus httpStatus;

	public HttpRequestError(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
