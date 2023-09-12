package com.example.bookstore.auth.configs;

import static com.example.bookstore.auth.utils.Constants.Profiles.DEV;

import com.example.bookstore.auth.errors.StartupException;
import com.example.bookstore.auth.security.auth.NormalAuthentication;
import com.example.bookstore.auth.security.filters.CorrelationIdFilter;
import com.example.bookstore.auth.security.filters.JwtAuthenticationFilter;
import com.example.bookstore.auth.security.utils.AsymmetricKeys;
import com.example.bookstore.auth.security.validations.JwtValidation;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
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
		if (Arrays.stream(profiles).noneMatch(DEV::equalsIgnoreCase)) {
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
