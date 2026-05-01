package usuarios.puertos;

import usuarios.repositorios.EntidadNoEncontrada;
import usuarios.repositorios.RepositorioException;
import usuarios.servicios.FactoriaServicios;
import usuarios.servicios.IServicioUsuarios;

public class ManejadorEventosImpl implements ManejadorEventos{

	private IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
	@Override
	public void compraventaCreada(String idVendedor, String idComprador)
			throws RepositorioException, EntidadNoEncontrada {
		servicio.sumarVentas(idVendedor);
		System.out.println("Contador de ventas incrementado para el id de usuario: " + idVendedor);
		servicio.sumarCompras(idComprador);
		System.out.println("Contador de compras incrementado para el id de usuario: " + idComprador);
	}

	
}
