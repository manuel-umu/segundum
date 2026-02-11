package repositorios.especificos;

import modelo.Producto;
import repositorios.RepositorioJPA;

public class RepositorioProductosJPA extends RepositorioJPA<Producto>{
	
	@Override
	public Class<Producto> getClase(){
		return Producto.class;
	}

}
