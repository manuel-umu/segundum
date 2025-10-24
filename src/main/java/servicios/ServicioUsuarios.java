package servicios;

import java.time.LocalDate;
import java.util.List;

import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.Repositorio;
import repositorios.RepositorioException;

public class ServicioUsuarios implements IServicioUsuarios {
	private Repositorio<Usuario, String> repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);

	@Override
	public String registrarUsuario(String nombre, String apellidos, String email, LocalDate fecha, String clave,
			String telefono) throws RepositorioException {
		// Control de integridad de los datos
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (apellidos == null || apellidos.isEmpty())
			throw new IllegalArgumentException("apellidos: no debe ser nulo ni vacio");

		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");

		if (fecha == null)
			throw new IllegalArgumentException("fecha: no debe ser nulo");

		if (fecha.isAfter(LocalDate.now()))
			throw new IllegalArgumentException("fecha: debe ser anterior a hoy");

		if (clave == null || clave.isEmpty())
			throw new IllegalArgumentException("clave: no debe ser nulo ni vacio");

		Usuario usuario = new Usuario(nombre, apellidos, email, fecha, clave, telefono);
		return repositorio.add(usuario);
	}
	
	/*
	 * Los parametros no cambiados deben de ser los originales
	 */
	void modificarUsuario(String id, String nombre, String apellidos, String clave, LocalDate fecha, String telefono) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = repositorio.getById(id);
		Usuario u2 = new Usuario(nombre, apellidos, u.getEmail(), fecha, clave, telefono);
		u2.setId(u.getId());
		repositorio.update(u2);
	}
	
	/*
	 * Dado un email comprobar si existe en el repositorio un usuario con dicho email
	 */
	private boolean existeUsuario(String email) throws RepositorioException {
		List<Usuario> usuarios = repositorio.getBy();
		for(Usuario u : usuarios) {
			if(u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
}
