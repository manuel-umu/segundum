package repositorios;

import modelo.Producto;

public class RepositorioProductos extends RepositorioJPA<Producto>{

	@Override
	public Class<Producto> getClase(){
		return Producto.class;
	}
}
