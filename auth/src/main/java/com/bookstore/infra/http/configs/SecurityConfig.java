package com.bookstore.infra.http.configs;

import com.bookstore.constants.ProfileConstants;
import com.bookstore.errors.StartupException;
import com.bookstore.infra.http.filters.CorrelationIdFilter;
import com.bookstore.infra.http.filters.JwtAuthenticationFilter;
import com.bookstore.infra.http.security.auth.NormalAuthentication;
import com.bookstore.infra.http.security.utils.AsymmetricKeys;
import com.bookstore.infra.http.security.validations.JwtValidation;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
@ConditionalOnProperty(prefix = "spring.security", value = {"enabled"}, havingValue = "true", matchIfMissing = true)
public class SecurityConfig {
	private static final String[] FREE_URLS = new String[]{
		"/erro*",
		"/erro*/**",
		"/actuator",
		"/actuator/**",
		"/swagger*",
		"/swagger*/**",
		"/v3/api-docs*",
		"/v3/api-docs/**",
		"/webjars/**",
		"/favicon.ico"
	};
	private final CustomSecurityProperties securityProperties;

	@PostConstruct
	private void postConstruct() {
		log.info("'{}' habilitado!", SecurityConfig.class.getSimpleName());
	}

	@Bean
	public SimpleAuthorityMapper simpleAuthorityMapper() {
		SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
		simpleAuthorityMapper.setConvertToUpperCase(true);
		return simpleAuthorityMapper;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity httpSecurity
	) throws Exception {
		httpSecurity
			.addFilterBefore(new CorrelationIdFilter(), ChannelProcessingFilter.class)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorizeHttpRequests())
			.sessionManagement(session())
		;

		if (securityProperties.isEnabled()) {
			httpSecurity.oauth2ResourceServer(oauth2ResourceServer());
		}

		return httpSecurity.build();
	}

	public Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oauth2ResourceServer() {
		return oauth2 ->
			oauth2
				.jwt(jwt())
//				.bearerTokenResolver(new )
//				.bearerTokenResolver(new DefaultBearerTokenResolver())
//				.authenticationEntryPoint(new CustomAuthenticationEntryPoint(keycloakProperties))
//				.accessDeniedHandler(new CustomAccessDeniedHandler(keycloakProperties))
			;
	}

	public Customizer<SessionManagementConfigurer<HttpSecurity>> session() {
		return s -> s.sessionCreationPolicy(STATELESS);
	}

	public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequests() {
		return authorization -> {
			authorization
				.requestMatchers(FREE_URLS)
				.permitAll();

//			for (var endpoint : securityProperties.getEndpoints()) {
//				var roles = endpoint.getRoles().parallelStream().map(String::toUpperCase).toArray(String[]::new);
//				log.debug("Configurando endpoint '{}' com roles '{}'", endpoint.getPattern(), roles);
//
//				authorization
//					.requestMatchers(endpoint.getPattern())
//					.hasAnyRole(roles);
//			}
		};
	}

	private Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> jwt() {
		return jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(
			null
//			new CustomJwtAuthenticationConverter(
//				keycloakProperties,
//				simpleAuthorityMapper(),
//				jwtAuthenticationConverter()
//			)
		);
	}
}
