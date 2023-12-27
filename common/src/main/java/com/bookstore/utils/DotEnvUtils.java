package com.bookstore.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DotEnvUtils {

	/**
	 * Apenas le as variaveis de ambiente do arquivo .env e adiciona
	 * as properties do sistema caso o profile no parametro existir
	 * no contexto de execução
	 */
	public static boolean load(String profile, Path path) {
		Path project = FileSystems.getDefault().getPath("").toAbsolutePath();
		if (path != null && Files.exists(path)) {
			project = path;
		}

		log.info("Procurando arquivo .env no diretório {}", project);
		if (!Files.exists(project.resolve(".env"))) {
			log.info("Arquivo .env não encontrado, pulando carregamento!");
			return false;
		}

		log.info("Carregando arquivo .env");
		Dotenv dotenv = Dotenv.configure().directory(project.toString()).ignoreIfMissing().load();
		String activeProfiles = dotenv.get("SPRING_PROFILES_ACTIVE");
		if (activeProfiles == null || activeProfiles.isEmpty()) {
			log.info("Profile de execução nulo, pulando carregamento do arquivo .env");
			return false;
		}

		String[] profiles = activeProfiles.split(",");
		if (!ArrayUtils.contains(profiles, profile)) {
			log.info(
				"Profiles '{}' de execução não inclui '{}', pulando carregamento do arquivo .env",
				profiles,
				profile
			);
			return false;
		}

		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		log.info(
			"Variáveis de ambiente do arquivo .env foram incluidas nas propriedades da aplicação!"
		);
		return true;
	}
}
