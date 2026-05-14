package usuarios.puertos;

import usuarios.modelo.UsuarioRolEnum;
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
	
	@Override
	public void valoracionCreada(String id, String rol, double puntuacion) 
			throws RepositorioException, EntidadNoEncontrada{
		UsuarioRolEnum rolE = UsuarioRolEnum.valueOf(rol);
		if(UsuarioRolEnum.COMPRADOR.equals(rolE)) {
			servicio.mediaComprador(id, puntuacion);
			System.out.println("Media del usuario comprador actualizada para: " + id);
		} else if(UsuarioRolEnum.VENDEDOR.equals(rolE)) {
			servicio.mediaVendedor(id, puntuacion);
			System.out.println("Media del usuario vendedor actualizada para: " + id);
		}
		
	}

}
