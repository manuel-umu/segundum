package productos.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import productos.dto.ProductoDTO;
import productos.dto.ProductoResDTO;
import productos.enumerados.EnumEstado;
import productos.modelo.Producto;
import productos.modelo.ProductoRes;
import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;

public interface IServicioProductos {

	String crear(String titulo, String descripcion, Float precio, EnumEstado estado, String idCategoria,
			Boolean envioDispo, String idVendedor) throws RepositorioException, EntidadNoEncontrada;

	void actualizar(String id, Float precio, String descripcion) throws RepositorioException, EntidadNoEncontrada;

	void asignarRecogida(String id, Double longitud, Double latitud, String descLugar)
			throws RepositorioException, EntidadNoEncontrada;

	Producto recuperar(String id) throws RepositorioException, EntidadNoEncontrada;

	void borrar(String id) throws RepositorioException, EntidadNoEncontrada;

	List<Producto> listar() throws RepositorioException;

	Page<ProductoResDTO> getListadoPaginado(Pageable pageable);

	void ponerVendido(String id) throws RepositorioException, EntidadNoEncontrada;

	void añadirVisualizacion(String id) throws RepositorioException, EntidadNoEncontrada;

	List<ProductoRes> historialMes(Integer mes, Integer year) throws RepositorioException;

	void modificarProducto(String id, Float precio, String descripcion)
			throws RepositorioException, EntidadNoEncontrada;

}
