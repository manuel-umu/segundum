package compraventas.servicios;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import compraventas.dto.CompraventaOutputDTO;

public interface IServicioCompraventas {

	public String registrarCompraventa(String idProducto, String idComprador) throws IOException;

	public Page<CompraventaOutputDTO> recuperarCompras(String idUsuario, Pageable pageable);

	public Page<CompraventaOutputDTO> recuperarVentas(String idUsuario, Pageable pageable);

	public Page<CompraventaOutputDTO> recuperarCompraventas(String idComprador, String idVendedor, Pageable pageable);
	
	public CompraventaOutputDTO recuperarCompraventa(String id);
}
