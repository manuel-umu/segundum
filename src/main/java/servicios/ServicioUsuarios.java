package servicios;

import java.time.LocalDate;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import repositorios.especificos.RepositorioUsuariosAdHoc;

public class ServicioUsuarios implements IServicioUsuarios {
	private RepositorioUsuariosAdHoc repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);

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

		if (!repositorio.isRegistrado(email)) { // Si ya esta registrado, devolver null
			Usuario usuario = new Usuario(nombre, apellidos, email, fecha, clave, telefono);
			return repositorio.add(usuario);
		}else {
			return null;
		}
		

	}

	/*
	 * Los parametros no cambiados deben de ser los originales
	 */
	@Override
	public void modificarUsuario(String id, String nombre, String apellidos, String clave, LocalDate fecha,
			String telefono) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = repositorio.getById(id);
		if (nombre != null && !nombre.isEmpty()) {
			u.setNombre(nombre);
		}
		if (apellidos != null && !apellidos.isEmpty()) {
			u.setApellidos(apellidos);
		}
		if (clave != null && !clave.isEmpty()) {
			u.setClave(clave);
		}
		if (fecha != null) {
			u.setFechaNac(fecha);
		}
		if (telefono != null && !telefono.isEmpty()) {
			u.setTelefono(telefono);
		}
		repositorio.update(u);
	}

	@Override
	public Usuario getUsuario(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(id);
	}

}
