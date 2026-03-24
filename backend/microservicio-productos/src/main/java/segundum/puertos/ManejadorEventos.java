package segundum.puertos;

import segundum.repositorios.EntidadNoEncontrada;
import segundum.repositorios.RepositorioException;

public interface ManejadorEventos {
	void compraventaCreada(String idProducto) throws RepositorioException, EntidadNoEncontrada;
	
}
