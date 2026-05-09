package compraventas.adaptadores;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import compraventas.dto.ProductoInfoDTO;
import compraventas.puertos.IClienteProductos;
import compraventas.puertos.ProductosRestClient;
import retrofit2.Response;

@Component
public class ClienteProductosRetrofit implements IClienteProductos{

	@Autowired
	private ProductosRestClient client;
	
	@Override
	public ProductoInfoDTO getProducto(String id) {
		try {
			Response<ProductoInfoDTO> response = client.getProducto(id).execute();
			if(!response.isSuccessful() || response.body() == null)
				throw new IllegalArgumentException("Producto no encontrado: " + id);
			return response.body();
		} catch (IOException e) {
			throw new RuntimeException("Error al conectar con Productos :", e);
		}
	}
	
}
