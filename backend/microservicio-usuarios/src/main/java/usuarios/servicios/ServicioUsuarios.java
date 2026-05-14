package usuarios.servicios;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import usuarios.dto.UsuarioDTO;
import usuarios.modelo.Usuario;
import usuarios.eventos.*;
import usuarios.adaptadores.*;
import usuarios.puertos.*;
import usuarios.repositorios.EntidadNoEncontrada;
import usuarios.repositorios.FactoriaRepositorios;
import usuarios.repositorios.RepositorioException;
import usuarios.repositorios.especificos.RepositorioUsuariosAdHoc;

public class ServicioUsuarios implements IServicioUsuarios {
	private RepositorioUsuariosAdHoc repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);

	@Override
	public String crear(String nombre, String apellidos, String email, LocalDate fecha, String clave, String telefono, boolean isAdmin)
			throws RepositorioException {
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

		Usuario usuario = new Usuario(nombre, apellidos, email, fecha, clave, telefono, isAdmin);
		String id = repositorio.add(usuario);
		
		try {
			EventoUsuarioCreado evento = new EventoUsuarioCreado(id, nombre, apellidos, email);
			IPublicadorEventos publicador = new PublicadorEventosRabbitMQ();
			publicador.publicarEvento(evento);
		} catch (Exception e) {
			System.err.println("Error publicando evento usuario-creado: " + e.getMessage());
		}
		return id;
	}

	/*
	 * Los parametros no cambiados deben de ser los originales
	 */
	@Override
	public void actualizar(String id, String nombre, String apellidos, String clave, LocalDate fecha, String telefono)
			throws RepositorioException, EntidadNoEncontrada {
		Usuario u = recuperar(id);
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
		try {
			EventoUsuarioModificado evento = new EventoUsuarioModificado(id, nombre, apellidos);
			IPublicadorEventos publicador = new PublicadorEventosRabbitMQ();
			publicador.publicarEvento(evento);
		} catch (Exception e) {
			System.err.println("Error publicando evento usuario-modificado: " + e.getMessage());
		}
	}

	@Override
	public Usuario recuperar(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(id);
	}

	@Override
	public List<UsuarioDTO> listar() throws RepositorioException {
		List<UsuarioDTO> dtos = new LinkedList<UsuarioDTO>();
		List<Usuario> usuarios = repositorio.getAll();
		for (Usuario u : usuarios) {
			dtos.add(toDto(u));
		}
		return dtos;
	}

	@Override
	public void borrar(String id) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = repositorio.getById(id);
		repositorio.delete(u);
	}

	@Override
	public Usuario login(String email, String password) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorio.getByEmail(email);
		if (usuario == null) {
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
		}
		if (!usuario.getClave().equals(password)) {
			throw new EntidadNoEncontrada("Contraseña incorrecta");
		}
		return usuario;
	}

	private UsuarioDTO toDto(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(),
				usuario.getFechaNac(), usuario.getClave(), usuario.getTelefono());
	}

	@Override
	public void sumarVentas(String idVendedor) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorio.getById(idVendedor);
		usuario.setContVentas(usuario.getContVentas() + 1);
		repositorio.update(usuario);
	}

	@Override
	public void sumarCompras(String idComprador) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorio.getById(idComprador);
		usuario.setContCompras(usuario.getContCompras() + 1);
		repositorio.update(usuario);
	}

	@Override
	public Usuario findByGithubId(String githubId) throws RepositorioException {
		return repositorio.getByGithubId(githubId);
	}

	@Override
	public void mediaComprador(String id, Double puntuacion) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorio.getById(id);
		int nuevo = usuario.getNumValoracionesComprador() + 1;
		double totalAntiguo = (usuario.getMediaComprador() * usuario.getNumValoracionesComprador());
		usuario.setMediaComprador((totalAntiguo + puntuacion) / nuevo);
		usuario.setNumValoracionesComprador(nuevo);
		repositorio.update(usuario);
	}

	@Override
	public void mediaVendedor(String id, Double puntuacion) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorio.getById(id);
		int nuevo = usuario.getNumValoracionesVendedor() + 1;
		double totalAntiguo = (usuario.getMediaVendedor() * usuario.getNumValoracionesVendedor());
		usuario.setMediaVendedor((totalAntiguo + puntuacion) / nuevo);
		usuario.setNumValoracionesVendedor(nuevo);
		repositorio.update(usuario);
	}
}
