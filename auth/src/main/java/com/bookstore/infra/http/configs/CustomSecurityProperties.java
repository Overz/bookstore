package com.bookstore.infra.http.configs;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("com.bookstore.security")
public class CustomSecurityProperties implements Serializable {
	private boolean enabled;

	@Getter
	@Setter
	@ToString
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Endpoints implements Serializable {
		private String pattern;
		private Set<String> roles;
	}
}
