package com.bookstore.gateway.controllers.privates;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/private/user")
public class UserController {

	@GetMapping("/current")
	public ResponseEntity<Object> getCurrentUser() {
		return ResponseEntity.ok("ok");
	}
}
