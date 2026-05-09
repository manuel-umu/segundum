package productos.puertos;

import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;

public interface ManejadorEventos {
	void compraventaCreada(String idProducto) throws RepositorioException, EntidadNoEncontrada;
	void usuarioCreado(String id, String nombre, String apaellidos, String email) throws RepositorioException;
	void usuarioModificado(String id, String nombre, String apaellidos) throws RepositorioException, EntidadNoEncontrada;	
}
