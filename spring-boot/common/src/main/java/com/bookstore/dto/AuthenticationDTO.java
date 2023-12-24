package com.bookstore.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public interface AuthenticationDTO extends Serializable {
	String getAccess_token();

	String getToken_type();

	Integer getExpires_in();

	ZonedDateTime getSignedAt();
}
