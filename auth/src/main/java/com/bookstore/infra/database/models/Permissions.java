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
public class Permissions implements Serializable {
	private String id;
}
