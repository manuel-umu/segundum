package productos.puertos;

import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;

public interface ManejadorEventos {
	void compraventaCreada(String idProducto) throws RepositorioException, EntidadNoEncontrada;
	
}
