package repositorios;

import modelo.Categoria;

public class RepositorioCategorias extends RepositorioJPA<Categoria>{

	@Override
	public Class<Categoria> getClase(){
		return Categoria.class;
	}
}
