package com.bookstore.enums;

import com.bookstore.utils.ParserUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatePatterns implements ValuedEnum<String> {
	DATE_TIME_PATTERN("yyyy-MM-dd HH:mm:ss.SSS"),
	ISO_8601("yyyy-MM-dd'T'HH:mm:ss.SSS");

	private final String value;

	public DatePatterns parse(String v) {
		return ParserUtils.getEnumValue(DatePatterns.class, v);
	}
}
