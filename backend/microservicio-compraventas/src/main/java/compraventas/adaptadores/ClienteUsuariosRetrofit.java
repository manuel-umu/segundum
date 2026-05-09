package compraventas.adaptadores;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import compraventas.dto.UsuariosInfoDTO;
import compraventas.puertos.IClienteUsuarios;
import compraventas.puertos.UsuariosRestClient;
import retrofit2.Response;

@Component
public class ClienteUsuariosRetrofit implements IClienteUsuarios {

	@Autowired
	private UsuariosRestClient client;
	
	@Override
	public UsuariosInfoDTO getNombreUsuario(String id) {
		try {
			Response<UsuariosInfoDTO> response = client.getNombreUsuario(id).execute();
			if(!response.isSuccessful() || response.body() == null)
				throw new IllegalArgumentException("Usuario no encontrado: " + id);
			return response.body();
		} catch (IOException e) {
			throw new RuntimeException("Error al conectar con Usuarios", e);
		}
	}
}
