package com.bookstore.infra.http.security.auth;

import org.springframework.security.core.Authentication;

public interface Authenticate<T> {
	Authentication execute(T o);
}
