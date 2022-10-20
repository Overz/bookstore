package com.example.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	private static void exit(int code, String msg) {
		if (code != 0) {
			System.out.println(msg);
			System.exit(code);
		}
	}

	private static void finish() {}
}
