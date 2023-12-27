package com.bookstore.infra.http.security.validations;

public interface Validator<R, T> {
	R execute(T o);
}
