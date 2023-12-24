package com.bookstore.domain.iam.dto;

import com.bookstore.infra.database.models.User;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class UserDTO implements Serializable {
	private String id;

	public User toModel() {
		return null;
	}
}
