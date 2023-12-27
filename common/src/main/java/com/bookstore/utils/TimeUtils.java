package com.bookstore.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TimeUtils {

	public static ZonedDateTime getSaoPauloDateTime() {
		return getZonedDateTime("America/Sao_Paulo");
	}

	public static ZonedDateTime getZonedDateTime(String tz) {
		return ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(tz));
	}
}
