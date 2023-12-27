package com.bookstore.infra.database.models;

import com.bookstore.enums.ValuedEnum;
import com.bookstore.utils.ParserUtils;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
//@Entity
//@Table(name = User.TABLE)
public class User implements Serializable {
	public static final String TABLE = "user";

	//	@Id
//	@Column(name = Fields.cdUser, nullable = false)
	private String cdUser;
	//	@Column(name = Fields.nmUser, nullable = false)
	private String nmUser;
	//	@Column(name = Fields.nmPassword, nullable = false)
	private String nmPassword;
	//	@Column(name = Fields.nmEmail, nullable = false)
	private String nmEmail;
	//	@Column(name = Fields.nuPhone, nullable = false)
	private String nuPhone;
	//	@Enumerated(EnumType.STRING)
//	@Convert(converter = UserStatusConverter.class)
//	@Column(name = Fields.flStatus, nullable = false)
	private UserStatus flStatus;

	//	@PrePersist
	private void prePersist() {
		cdUser = UUID.randomUUID().toString();
	}

	@Getter
	@AllArgsConstructor
	public enum UserStatus implements ValuedEnum<String> {
		ACTIVE("A"),
		INACTIVE("I"),
		;

		private final String value;

		public static UserStatus parse(String v) {
			return ParserUtils.getEnumValue(UserStatus.class, v);
		}
	}
}
