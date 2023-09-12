package com.example.bookstore.auth.security.utils;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorities implements Serializable {
	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";
}
