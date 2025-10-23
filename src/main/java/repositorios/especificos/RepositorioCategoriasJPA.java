package repositorios.especificos;

import modelo.Categoria;
import repositorios.RepositorioJPA;

public class RepositorioCategoriasJPA extends RepositorioJPA<Categoria> {
	
	@Override
	public Class<Categoria> getClase() {
		return Categoria.class;
	}
}
