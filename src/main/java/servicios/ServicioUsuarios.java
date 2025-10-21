package servicios;

import java.time.LocalDate;

import modelo.Usuario;
import repositorios.FactoriaRepositorios;
import repositorios.Repositorio;
import repositorios.RepositorioUsuarios;
import repositorios.RepositorioException;

public class ServicioUsuarios implements IServicioUsuario {

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

		if (fecha.isAfter(LocalDate.now()) || apellidos.isEmpty())
			throw new IllegalArgumentException("fecha: debe ser anterior a hoy");

		if (clave == null || clave.isEmpty())
			throw new IllegalArgumentException("opciones: no debe ser una coleccion nula");

		Usuario usuario = new Usuario(nombre, apellidos, email, fecha, clave, telefono);

		String id = repositorio.add(usuario);

		return id;
	}
}
