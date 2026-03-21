package repositorios.especificos;

import modelo.Usuario;
import repositorios.RepositorioException;
import repositorios.RepositorioString;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

	// TODO
	public boolean isRegistrado(String email) throws RepositorioException;

	public Usuario getByEmail(String email) throws RepositorioException;
	
}
