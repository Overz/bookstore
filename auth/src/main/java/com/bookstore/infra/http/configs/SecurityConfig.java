package com.bookstore.infra.http.configs;

import com.bookstore.constants.ProfileConstants;
import com.bookstore.errors.StartupException;
import com.bookstore.infra.http.filters.CorrelationIdFilter;
import com.bookstore.infra.http.filters.JwtAuthenticationFilter;
import com.bookstore.infra.http.security.auth.NormalAuthentication;
import com.bookstore.infra.http.security.utils.AsymmetricKeys;
import com.bookstore.infra.http.security.validations.JwtValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.io.IOException;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Value("#{environment.getActiveProfiles()}")
	private String[] profiles;

	@Value("${spring.security.files.path}")
	private String asymmetricKeysPath;

	@Value("${spring.security.tokens.jwt.exp}")
	private long expiresAt;

	@PostConstruct
	private void init() throws StartupException, IOException {
		if (Arrays.stream(profiles).noneMatch(ProfileConstants.DEV::equalsIgnoreCase)) {
			AsymmetricKeys.init(asymmetricKeysPath);
		}
	}

	private CorrelationIdFilter getCorrelationIdFilter() {
		return new CorrelationIdFilter();
	}

	private JwtAuthenticationFilter getJwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(
			profiles,
			new JwtValidation(asymmetricKeysPath, expiresAt),
			new NormalAuthentication()
		);
	}

	@Bean
	public SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		return http
			.addFilterAt(getCorrelationIdFilter(), ChannelProcessingFilter.class)
			.addFilterBefore(getJwtAuthenticationFilter(), AuthorizationFilter.class)
			.authorizeHttpRequests(
				authorization ->
					authorization
						.requestMatchers("**/private/**")
						.authenticated()
						/*.access(new AuthorizationHandler())*/.anyRequest()
						.permitAll()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.build();
	}
	//	@Bean
	//	public AuthenticationFailureHandler authenticationFailureHandler() {
	//		return new AuthenticationErrorHandler();
	//		return null;
	//	}
}
