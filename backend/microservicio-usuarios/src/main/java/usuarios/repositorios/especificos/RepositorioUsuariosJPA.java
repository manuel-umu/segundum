package usuarios.repositorios.especificos;

import usuarios.modelo.Usuario;
import usuarios.repositorios.RepositorioJPA;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario> {
	
	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}
}
