package puertos;

import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public interface ManejadorEventos {

	void compraventaCreada(String idVendedor, String idComprador) throws RepositorioException, EntidadNoEncontrada;
}
