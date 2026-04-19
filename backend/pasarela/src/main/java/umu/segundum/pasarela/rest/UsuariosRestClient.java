package umu.segundum.pasarela.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import umu.segundum.pasarela.dto.LoginDTO;
import umu.segundum.pasarela.dto.UsuarioDTO;

// Interfaz Retrofit para comunicacion con el microservicio usuarios y verificar credenciales
public interface UsuariosRestClient {

	@POST("usuarios/verify-credentials")
	Call<UsuarioDTO> verifyCredentials(@Body LoginDTO credenciales);

	@GET("usuarios/github/{githubId}")
	Call<UsuarioDTO> getByGithubId(@Path("githubId") String githubId);
}