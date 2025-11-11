package repositorios;

import modelo.Usuario;

public class RepositorioUsuarios extends RepositorioJPA<Usuario> {

	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}
}
