package com.bookstore.validations;

import com.bookstore.errors.ValidationException;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ValidationUtils {

	public static void checkNotEmpty(String s, String msg) {
		if (s == null || s.isEmpty()) {
			throw new ValidationException(msg);
		}
	}

	public static void checkNotNull(Object o, String msg) {
		if (o == null) {
			throw new ValidationException(msg);
		}
	}

	public static void checkNotContains(Collection<?> collection, Object o, String msg) {
		if (!collection.contains(o)) {
			throw new ValidationException(msg);
		}
	}

	public static void checkEquals(String a, String b, String msg) {
		if (!a.equalsIgnoreCase(b)) {
			throw new ValidationException(msg);
		}
	}
}
