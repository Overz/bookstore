package com.bookstore.gateway.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/public")
public class AuthController {

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(String email, String password) {
		return null;
	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout() {
		return null;
	}
}
