package servicios;

import java.time.LocalDate;

import repositorios.RepositorioException;

public interface IServicioUsuarios {
	public String registrarUsuario(String nombre, String apellidos, String email, LocalDate fecha, String clave, String telefono) throws RepositorioException;

}
