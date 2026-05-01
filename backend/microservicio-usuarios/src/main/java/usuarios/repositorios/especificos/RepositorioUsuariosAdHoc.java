package usuarios.repositorios.especificos;

import usuarios.modelo.Usuario;
import usuarios.repositorios.RepositorioException;
import usuarios.repositorios.RepositorioString;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

	// TODO
	public boolean isRegistrado(String email) throws RepositorioException;

	public Usuario getByEmail(String email) throws RepositorioException;

	public Usuario getByGithubId(String githubId) throws RepositorioException;
}
