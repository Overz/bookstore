package com.bookstore.infra.http.security.validations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.bookstore.errors.ForbiddenError;
import com.bookstore.errors.InternalServerError;
import com.bookstore.infra.http.security.utils.HeadersUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Slf4j
public class JwtValidation implements Validator<DecodedJWT, HttpServletRequest> {
	private final String secret;
	private final long expiresAt;

	public JwtValidation(String secret, long expiresAt) {
		this.secret = secret;
		this.expiresAt = expiresAt;
	}

	@Override
	public DecodedJWT execute(HttpServletRequest request) throws ForbiddenError, InternalServerError {
		String token = HeadersUtils.getHeaderAuthorization(request);

		Algorithm algorithm;
		try {
			algorithm = Algorithm.HMAC256(secret);
		} catch (IllegalArgumentException e) {
			throw new InternalServerError("Erro criando o algoritimo para decode do JWT", e);
		}

		DecodedJWT jwt;
		try {
			jwt = JWT.decode(token);
		} catch (JWTDecodeException e) {
			throw new InternalServerError("Erro decodando o JWT", e);
		}

		try {
			algorithm.verify(jwt);
		} catch (SignatureVerificationException e) {
			throw new ForbiddenError("Assinatura JWT não experada!");
		}

		try {
			Instant expirationTime = Instant.now().plus(expiresAt, ChronoUnit.SECONDS);
			long time = expirationTime.getLong(ChronoField.INSTANT_SECONDS);

			JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(time).build();
			verifier.verify(jwt);
		} catch (JWTVerificationException e) {
			throw new ForbiddenError("Erro verificando o JWT", e);
		}

		return jwt;
	}
}
