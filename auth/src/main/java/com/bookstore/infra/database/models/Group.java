package com.bookstore.infra.database.models;

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
//@Entity
//@Table(name = Group.TABLE)
public class Group implements Serializable {
	public static final String TABLE = "group";

	//	@Id
//	@Column
	private String cdGroup;
}
