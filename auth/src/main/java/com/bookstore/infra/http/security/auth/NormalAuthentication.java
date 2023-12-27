package com.bookstore.infra.http.security.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bookstore.infra.http.security.utils.Permissions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class NormalAuthentication implements Authenticate<DecodedJWT> {

	@Override
	public Authentication execute(DecodedJWT jwt) {
		Permissions permissions = Permissions.SCHEDULER;
		//		return SchedulerPrincipal
		//			.builder()
		//			.name(jwt.getSubject())
		//			.authenticated(true)
		//			.credentials("")
		//			.authorities(permissions.getAuthorities())
		//			.roles(permissions.getRoles())
		//			.build();

		UserDetails userDetails = User
			.withUsername(jwt.getSubject())
			.password("")
			.roles(permissions.getRoles())
			.authorities(permissions.getGroups())
			.build();

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
}
