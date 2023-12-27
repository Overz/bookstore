package com.bookstore.enums;

import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SSLContextProtocol {
	SSL("SSL"),
	TLS("TLS"),
	TLS_1("TLSv1.0"),
	TLS_1_1("TLSv1.1"),
	TLS_1_2("TLSv1.2"),
	TLS_1_3("TLSv1.3");

	private final String value;

	public static SSLContextProtocol parse(String value) {
		for (var e : values()) {
			if (e.getValue().equalsIgnoreCase(value)) {
				return e;
			}
		}

		throw new NoSuchElementException(
			"Nenhuma versão do SSL Context encontrado com o valor: " + value
		);
	}
}
