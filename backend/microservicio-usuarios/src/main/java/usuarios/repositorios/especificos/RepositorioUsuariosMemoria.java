package usuarios.repositorios.especificos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import usuarios.modelo.Usuario;
import usuarios.repositorios.EntidadNoEncontrada;
import usuarios.repositorios.RepositorioException;

// Para pruebas
public class RepositorioUsuariosMemoria implements RepositorioUsuariosAdHoc {

	private static final Map<String, Usuario> bbdd = new LinkedHashMap<>();

	@Override
	public String add(Usuario usuario) throws RepositorioException {
		String id = UUID.randomUUID().toString();
		usuario.setId(id);
		bbdd.put(id, usuario);
		System.out.println("Se ha añadido el usuario " + usuario.toString());
		return id;
	}

	@Override
	public void update(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		if (!bbdd.containsKey(usuario.getId())) {
			throw new EntidadNoEncontrada("Usuario no encontrado: " + usuario.getId());
		}
		System.out.println("Se ha modificado el usuario " + usuario.toString());
		bbdd.put(usuario.getId(), usuario);
	}

	@Override
	public void delete(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		if (bbdd.remove(usuario.getId()) == null) {
			throw new EntidadNoEncontrada("Usuario no encontrado: " + usuario.getId());
		}
		System.out.println("Se ha eliminado el usuario " + usuario.toString());
	}

	@Override
	public Usuario getById(String id) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = bbdd.get(id);
		if (u == null)
			throw new EntidadNoEncontrada("Usuario no encontrado: " + id);
		System.out.println("Info del usuario " + u.toString());
		return u;
	}

	@Override
	public List<Usuario> getAll() throws RepositorioException {
		return new ArrayList<>(bbdd.values());
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		return new ArrayList<>(bbdd.keySet());
	}

	@Override
	public boolean isRegistrado(String email) throws RepositorioException {
		return bbdd.values().stream().anyMatch(u -> email.equals(u.getEmail()));
	}

	@Override
	public Usuario getByEmail(String email) throws RepositorioException {
		Usuario usuario = bbdd.values().stream().filter(u -> email.equals(u.getEmail())).findFirst().orElse(null);
		System.out.println("Se ha encontrado por email esto: " + usuario);
		return usuario;
	}

	@Override
	public Usuario getByGithubId(String githubId) throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}
}
