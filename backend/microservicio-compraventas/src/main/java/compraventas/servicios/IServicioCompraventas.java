package compraventas.servicios;

import java.io.IOException;
import java.util.List;

import compraventas.modelo.Compraventa;

public interface IServicioCompraventas {

	public String registrarCompraventa(String idProducto, String idComprador) throws IOException;
	
	public List<Compraventa> recuperarCompras(String idUsuario);
	
	public List<Compraventa> recuperarVentas(String idUsuario);
	
	public List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor);
}
