package usuarios.puertos;

import usuarios.repositorios.EntidadNoEncontrada;
import usuarios.repositorios.RepositorioException;

public interface ManejadorEventos {

	void compraventaCreada(String idVendedor, String idComprador) throws RepositorioException, EntidadNoEncontrada;

	void valoracionCreada(String id, String rol, double puntuacion) throws RepositorioException, EntidadNoEncontrada;
}
