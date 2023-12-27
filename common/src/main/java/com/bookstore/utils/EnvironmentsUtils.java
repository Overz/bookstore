package com.bookstore.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class EnvironmentsUtils {

	public static String getValueWithErr(String k) {
		String v = getValue(k);
		if (v == null) {
			throw new NullPointerException("Variável '" + k + "' nula identificada!");
		}

		return v;
	}

	public static String getValue(String k) {
		var v = System.getenv(k);
		return v != null ? v : System.getProperty(k);
	}
}
