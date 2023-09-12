package com.example.bookstore.auth.controllers.system;

import static com.example.bookstore.auth.utils.Constants.Profiles.DEV;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith({ MockitoExtension.class })
class LivenessControllerTest extends Assertions {
	@InjectMocks
	LivenessController ctrl;

	final String name = "batata";
	final String version = "0.0.1";
	final String artifact = "auth";
	final String group = "com.example.bookstore";
	final String[] profiles = new String[] { DEV };

	BuildProperties buildProperties;

	@BeforeEach
	void beforeAll() {
		Properties p = new Properties();
		p.setProperty("name", name);
		p.setProperty("version", version);
		p.setProperty("artifact", artifact);
		p.setProperty("group", group);
		buildProperties = new BuildProperties(p);
	}

	@Test
	@DisplayName("Should return information from /health endpoint")
	void healthTest_Ok() {
		ReflectionTestUtils.setField(ctrl, "profiles", profiles);
		ReflectionTestUtils.setField(ctrl, "props", buildProperties);

		LocalDateTime now = LocalDateTime.now();
		ResponseEntity<Map<String, Object>> res = ctrl.health();
		Map<String, Object> body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertNotNull(body);
		assertEquals(name, body.get("name"));
		assertEquals(version, body.get("version"));
		assertEquals(profiles, body.get("profiles"));

		assertTrue(now.isBefore((LocalDateTime) body.get("date")));
	}
}
