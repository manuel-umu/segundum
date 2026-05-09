package compraventas.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import compraventas.puertos.ProductosRestClient;
import compraventas.puertos.UsuariosRestClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitContrl {

	@Value("${retrofit.usuarios.baseUrl}")
	private String usuariosBaseUrl;

	@Value("${retrofit.productos.baseUrl}")
	private String productosBaseUrl;

	@Bean
	public UsuariosRestClient clienteUsuarios() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(usuariosBaseUrl)
				.addConverterFactory(GsonConverterFactory.create()).build();
		UsuariosRestClient service = retrofit.create(UsuariosRestClient.class);
		return service;
	}

	@Bean
	public ProductosRestClient clienteProductos() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(productosBaseUrl)
				.addConverterFactory(GsonConverterFactory.create()).build();
		ProductosRestClient service = retrofit.create(ProductosRestClient.class);
		return service;

	}

}
