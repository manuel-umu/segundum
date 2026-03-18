package segundum.servicios;

import java.util.List;

import segundum.modelo.Compraventa;

public interface IServicioCompraventas {

	public String registrarCompraventa(String idProducto, String idComprador);
	
	public List<Compraventa> recuperarCompras(String idUsuario);
	
	public List<Compraventa> recuperarVentas(String idUsuario);
	
	public List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor);
}
