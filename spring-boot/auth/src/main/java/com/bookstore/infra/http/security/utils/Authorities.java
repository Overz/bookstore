package com.bookstore.infra.http.security.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorities implements Serializable {
	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";
}
