package com.bookstore.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class MapperUtils {
	@Getter
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static synchronized String toJson(Object o) throws IOException {
		return objectMapper.writeValueAsString(o);
	}

	public static synchronized <T> T fromJson(String json, Class<T> cls) throws IOException {
		return objectMapper.readValue(json, cls);
	}

	public static synchronized <T> T fromJson(InputStream is, Class<T> cls) throws IOException {
		return objectMapper.readValue(is, cls);
	}

	public static synchronized <T> T convert(Object o, Class<T> cls) throws IOException {
		return fromJson(toJson(o), cls);
	}
}
