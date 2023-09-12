package com.example.bookstore.auth.security.utils;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions implements Serializable {
	SCHEDULER(new String[] { Authorities.USER }, new String[] { Roles.ACCESS_PRIVATE_ENDPOINTS });

	private final String[] groups;
	private final String[] roles;
}
