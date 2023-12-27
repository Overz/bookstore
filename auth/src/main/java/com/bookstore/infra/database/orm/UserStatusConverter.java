package com.bookstore.infra.database.orm;

import com.bookstore.infra.database.models.User.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
	@Override
	public String convertToDatabaseColumn(UserStatus attribute) {
		return attribute != null ? attribute.getValue() : null;
	}

	@Override
	public UserStatus convertToEntityAttribute(String dbData) {
		return dbData != null ? UserStatus.parse(dbData) : null;
	}
}
