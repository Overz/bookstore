package com.bookstore.infra.http.security.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Roles implements Serializable {
	public static final String ACCESS_PRIVATE_ENDPOINTS = "ACCESS_PRIVATE_ENDPOINTS";
}
