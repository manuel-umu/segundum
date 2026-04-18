package segundum.puertos;

import segundum.dto.ProductoInfoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductosRestClient {
	
	@GET("productos/{id}")
	Call<ProductoInfoDTO> getProducto(@Path("id") String id);
}
