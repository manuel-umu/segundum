package compraventas.puertos;

import compraventas.dto.UsuariosInfoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosRestClient {

	@GET("usuarios/{id}/nombre")
	Call<UsuariosInfoDTO> getNombreUsuario(@Path("id") String id);
}
