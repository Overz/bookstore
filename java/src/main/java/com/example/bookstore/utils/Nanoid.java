package com.example.bookstore.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.util.Random;

public class Nanoid {

	/**
	 * Gera um ID Unico
	 *
	 * @param length tamanho do ID
	 * @return nanoid
	 */
	public static String nanoid(int length) {
		return NanoIdUtils.randomNanoId(new Random(), NanoIdUtils.DEFAULT_ALPHABET, length);
	}
}
