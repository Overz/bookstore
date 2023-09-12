package com.example.bookstore.auth.utils;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants implements Serializable {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Profiles implements Serializable {
		public static final String PROD = "prod";
		public static final String TEST = "test";
		public static final String DEV = "dev";
	}
}
