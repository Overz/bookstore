package com.bookstore.infra.http.security.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum Permissions implements Serializable {
	SCHEDULER(new String[]{Authorities.USER}, new String[]{Roles.ACCESS_PRIVATE_ENDPOINTS});

	private final String[] groups;
	private final String[] roles;
}
