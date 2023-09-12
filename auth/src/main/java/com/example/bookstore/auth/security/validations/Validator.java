package com.example.bookstore.auth.security.validations;

public interface Validator<R, T> {
	R execute(T o);
}
