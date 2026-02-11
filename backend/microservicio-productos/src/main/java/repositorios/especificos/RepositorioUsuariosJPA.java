package repositorios.especificos;

import modelo.Usuario;
import repositorios.RepositorioJPA;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario> {
	
	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}
}
