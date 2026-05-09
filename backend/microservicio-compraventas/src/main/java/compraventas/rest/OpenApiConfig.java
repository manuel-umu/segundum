package compraventas.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("API Compraventas")
				.description("Microservicio de gestión de compraventas").version("1.0.0"));
	}
}
