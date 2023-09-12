package com.example.bookstore.auth.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParserUtils {

	public static <T extends Enum<T> & ValuedEnum<V>, V> T getEnumValue(Class<T> enumClass, V value) {
		for (T enumValue : enumClass.getEnumConstants()) {
			if (Objects.equals(enumValue.getValue(), value)) {
				return enumValue;
			}
		}
		return null;
	}

	public static LocalDateTime parseDate(DatePatterns patterns, String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patterns.getValue());
		return LocalDateTime.parse(date, formatter);
	}
}
