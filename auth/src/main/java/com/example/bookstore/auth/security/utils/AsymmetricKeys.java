package com.example.bookstore.auth.security.utils;

import com.example.bookstore.auth.errors.StartupException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsymmetricKeys implements Serializable {
	private static final Map<String, String> DATA = new HashMap<>();
	private static final String PEM_EXT = ".pem";

	public static void putAll(Map<String, String> content) {
		DATA.putAll(content);
	}

	public static void init(String path) throws IOException, StartupException {
		if (false) {
			File base = new File(path);
			File[] files = base.listFiles((dir, name) -> name.endsWith(PEM_EXT));
			if (files == null || files.length == 0) {
				throw new StartupException("Nenhum arquivo '" + PEM_EXT + "' encontrado!");
			}

			for (File f : files) {
				byte[] fileContent = Files.readAllBytes(f.toPath());
				if (fileContent.length == 0) {
					throw new StartupException("Arquivo '" + f.getName() + "' vazio!");
				}

				DATA.put(f.getName(), new String(fileContent));
			}
		}
	}
}
