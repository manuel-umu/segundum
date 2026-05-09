package usuarios.servicios;

import java.time.LocalDate;
import java.util.List;

import usuarios.dto.UsuarioDTO;
import usuarios.modelo.Usuario;
import usuarios.repositorios.EntidadNoEncontrada;
import usuarios.repositorios.RepositorioException;

public interface IServicioUsuarios {
	String crear(String nombre, String apellidos, String email, LocalDate fecha, String clave, String telefono, boolean isAdmin)
			throws RepositorioException;

	void actualizar(String id, String nombre, String apellidos, String clave, LocalDate fecha, String telefono)
			throws RepositorioException, EntidadNoEncontrada;

	Usuario recuperar(String id) throws RepositorioException, EntidadNoEncontrada;

	void borrar(String id) throws RepositorioException, EntidadNoEncontrada;

	List<UsuarioDTO> listar() throws RepositorioException;

	Usuario login(String email, String password) throws RepositorioException, EntidadNoEncontrada;

	void sumarVentas(String idVendedor) throws RepositorioException, EntidadNoEncontrada;

	void sumarCompras(String idComprador) throws RepositorioException, EntidadNoEncontrada;

	Usuario findByGithubId(String githubId) throws RepositorioException;
}
