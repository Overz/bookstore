package com.bookstore.infra.http.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro para criar "x-correlation-id" (UUID) para rastreabilidade de chamadas HTTP;
 */
@Slf4j
//@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
	private static final String HEADER = "X-Correlation-Id";

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	)
		throws ServletException, IOException {
		String correlationId = request.getHeader(HEADER);

		if (correlationId == null || correlationId.isEmpty()) {
			correlationId = UUID.randomUUID().toString();
		}

		log.info("Setting header '{}' with value '{}'", HEADER, correlationId);
		response.setHeader(HEADER, correlationId);

		filterChain.doFilter(request, response);
	}
}
