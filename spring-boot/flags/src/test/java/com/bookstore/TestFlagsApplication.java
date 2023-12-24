package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestFlagsApplication {

	public static void main(String[] args) {
		SpringApplication.from(FlagsApplication::main).with(TestFlagsApplication.class).run(args);
	}

}
