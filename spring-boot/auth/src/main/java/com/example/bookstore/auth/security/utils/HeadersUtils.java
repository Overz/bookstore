package com.example.bookstore.auth.security.utils;

import com.example.bookstore.auth.errors.ForbiddenError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeadersUtils {

	public static String getHeaderAuthorization(HttpServletRequest request) throws ForbiddenError {
		String authorization = request.getHeader("authorization");
		if (authorization == null || authorization.isEmpty()) {
			throw new ForbiddenError("Nenhum header authorization encontrado!");
		}

		String bearer = "bearer";
		if (!authorization.toLowerCase().startsWith(bearer)) {
			throw new ForbiddenError("Token não segue o padrão 'bearer'");
		}

		String token = authorization.substring(bearer.length()).trim();
		if (token.isEmpty()) {
			throw new ForbiddenError("Token não possui valor!");
		}

		return token;
	}
}
