package com.example.bookstore.auth.security.managers;

import java.util.function.Supplier;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public class CustomAuthorizationManager
	implements AuthorizationManager<RequestAuthorizationContext> {

	@Override
	public AuthorizationDecision check(
		Supplier<Authentication> authentication,
		RequestAuthorizationContext object
	) {
		// Obter a autenticação
		Authentication auth = authentication.get();

		// Lógica personalizada de autorização
		// ...

		// Retornar a decisão de autorização
		return new AuthorizationDecision(true); // ou AuthorizationDecision.accessDenied("Motivo")
	}
}
