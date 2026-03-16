package segundum;

import org.springframework.context.annotation.Configuration;

import segundum.enumerados.EnumEstado;
import segundum.modelo.Usuario;
import segundum.repositorios.RepositorioProductos;
import segundum.repositorios.RepositorioUsuarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(RepositorioUsuarios repository) {
		return args -> {
			log.info("Cargando usuario " + repository.save(new Usuario("Pedro", "Piedra", "pedro@piedra.com")));
		};
	}
}
