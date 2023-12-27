package com.bookstore.gateway.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class LivenessController {
	@Autowired
	private BuildProperties props;

	@Value("#{environment.getActiveProfiles()}")
	private String[] profiles;

	@GetMapping("/liveness")
	public ResponseEntity<Map<String, Object>> health() {
		Map<String, Object> data = new HashMap<>();
		data.put("name", props.getName());
		data.put("version", props.getVersion());
		data.put("profiles", profiles);
		data.put("date", LocalDateTime.now());

		return ResponseEntity.ok(data);
	}
}
