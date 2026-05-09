package compraventas.puertos;

import compraventas.dto.ProductoInfoDTO;

public interface IClienteProductos {
	ProductoInfoDTO getProducto(String id);
}
