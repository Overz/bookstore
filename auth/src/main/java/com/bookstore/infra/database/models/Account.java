package com.bookstore.infra.database.models;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
//@Entity
//@Table(name = Account.TABLE)
public class Account implements Serializable {
	public static final String TABLE = "activation";

	//	@Id
//	@Column(name = Fields.cdActivation, nullable = false)
	private String cdActivation;
	//	@Column(name = Fields.data)
	private String data;
	//	@Column(name = Fields.dtCreation, nullable = false)
	private LocalDateTime dtCreation;
}
