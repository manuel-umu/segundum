package servicios;

import java.time.LocalDate;
import java.util.List;

import dto.UsuarioDTO;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public interface IServicioUsuarios {
	String crear(String nombre, String apellidos, String email, LocalDate fecha, String clave, String telefono)
			throws RepositorioException;

	void actualizar(String id, String nombre, String apellidos, String clave, LocalDate fecha, String telefono)
			throws RepositorioException, EntidadNoEncontrada;

	Usuario recuperar(String id) throws RepositorioException, EntidadNoEncontrada;

	void borrar(String id) throws RepositorioException, EntidadNoEncontrada;

	List<UsuarioDTO> listar() throws RepositorioException;
	
	Usuario login(String email, String password) throws RepositorioException, EntidadNoEncontrada;
	
}
