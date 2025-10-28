package servicios;

import java.time.LocalDate;

import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public interface IServicioUsuarios {
	public String registrarUsuario(String nombre, String apellidos, String email, LocalDate fecha, String clave,
			String telefono) throws RepositorioException;

	public void modificarUsuario(String id, String nombre, String apellidos, String clave, LocalDate fecha,
			String telefono) throws RepositorioException, EntidadNoEncontrada;

	public Usuario getUsuario(String id) throws RepositorioException, EntidadNoEncontrada;
}
