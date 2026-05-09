package umu.segundum.pasarela.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import umu.segundum.pasarela.rest.UsuariosRestClient;

@Configuration
public class RetrofitConfig {

	@Value("${retrofit.usuarios.baseUrl}")
	private String usuariosBaseUrl;

	@Bean
	public UsuariosRestClient usuariosRestClient() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(usuariosBaseUrl)
				.addConverterFactory(GsonConverterFactory.create()).build();
		return retrofit.create(UsuariosRestClient.class);
	}
}