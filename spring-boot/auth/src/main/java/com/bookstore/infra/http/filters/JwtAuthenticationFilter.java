package com.bookstore.infra.http.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bookstore.infra.http.security.auth.Authenticate;
import com.bookstore.infra.http.security.validations.Validator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final String[] profiles;
	private final Validator<DecodedJWT, HttpServletRequest> validation;
	private final Authenticate<DecodedJWT> authenticated;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	)
		throws ServletException, IOException {
		DecodedJWT jwt = validation.execute(request);
		Authentication authentication = authenticated.execute(jwt);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getRequestURI().matches(".*/private/.*");
	}
}
