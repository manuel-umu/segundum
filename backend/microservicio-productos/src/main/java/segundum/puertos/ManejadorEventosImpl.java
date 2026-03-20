package segundum.puertos;

import org.springframework.beans.factory.annotation.Autowired;

import segundum.repositorios.EntidadNoEncontrada;
import segundum.repositorios.RepositorioException;
import segundum.servicios.IServicioProductos;

public class ManejadorEventosImpl implements ManejadorEventos {

	@Autowired
	private IServicioProductos servicio;
	
	@Override
	public void compraventaCreada(String idProducto) throws RepositorioException, EntidadNoEncontrada {
		servicio.ponerVendido(idProducto);
		System.out.println("Producto puesto como vendido: " + idProducto);
	}

	
}
