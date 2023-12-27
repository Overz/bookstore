package com.bookstore;

import com.bookstore.constants.ProfileConstants;
import com.bookstore.utils.DotEnvUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		var classpath = System.getProperty("java.class.path").split(":");
		var path = Path.of(classpath[0]).getParent().getParent().getParent().getParent();
		DotEnvUtils.load(ProfileConstants.DEV, path);

		System.setProperty("TMP_DIR", System.getProperty("java.io.tmpdir"));
		System.setProperty("START_TIME", "" + System.currentTimeMillis());
		SpringApplication.run(Main.class, args);
	}
}
