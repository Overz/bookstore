package com.example.bookstore.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
	exclude = {H2ConsoleAutoConfiguration.class, SecurityAutoConfiguration.class}
)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
