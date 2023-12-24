package com.bookstore.gateway.controllers.system;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemInfoController {

	@GetMapping("/memory")
	public ResponseEntity<Map<String, Object>> getMemory() {
		return null;
	}
}
