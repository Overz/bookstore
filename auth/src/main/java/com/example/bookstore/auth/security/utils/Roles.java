package com.example.bookstore.auth.security.utils;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Roles implements Serializable {
	public static final String ACCESS_PRIVATE_ENDPOINTS = "ACCESS_PRIVATE_ENDPOINTS";
}
