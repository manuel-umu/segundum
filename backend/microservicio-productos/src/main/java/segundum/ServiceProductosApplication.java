package segundum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import segundum.servicios.IServicioCategorias;

@SpringBootApplication
public class ServiceProductosApplication {

	@Autowired
	private IServicioCategorias servicioC;
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceProductosApplication.class, args);
	}


	@Bean
	public CommandLineRunner cargarCategorias() {
		return args -> {
			String[] categorias = {
					"segundum/categorias/Electronica.xml",
					"segundum/categorias/Ropa_y_accesorios.xml",
					"segundum/categorias/Multimedia.xml",
					"segundum/categorias/Casa_y_jardin.xml",
			};
			for (String ruta : categorias) {
				try {
					servicioC.cargarCategoria(ruta);
				}catch (Exception e) {
					System.err.println("Error cargando");
				}
			}
		};
	}

}
