package com.bookstore.infra.constants;

import com.bookstore.utils.EnvironmentsUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ApplicationConstants {
	public static final String TZ = EnvironmentsUtils.getValueWithErr("TZ");
}
