package com.example.bookstore.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatePatterns implements ValuedEnum<String> {
	DATE_TIME_PATTERN("yyyy-MM-dd HH:mm:ss.SSS"),
	ISO_8601("yyyy-MM-dd'T'HH:mm:ss.SSS");

	private final String value;

	public static DatePatterns parse(String v) {
		return ParserUtils.getEnumValue(DatePatterns.class, v);
	}
}
