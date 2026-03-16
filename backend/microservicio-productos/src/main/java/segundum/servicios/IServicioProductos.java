package segundum.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import segundum.dto.ProductoDTO;
import segundum.dto.UsuarioDTO;
import segundum.enumerados.EnumEstado;
import segundum.modelo.Producto;
import segundum.modelo.ProductoRes;
import segundum.repositorios.EntidadNoEncontrada;
import segundum.repositorios.RepositorioException;

public interface IServicioProductos {
	
	String crear(String titulo, String descripcion, Float precio, EnumEstado estado, String idCategoria, Boolean envioDispo, String idVendedor) throws RepositorioException, EntidadNoEncontrada;
	
	void actualizar(String id, Float precio, String descripcion) throws RepositorioException, EntidadNoEncontrada;

	void asignarRecogida(String id, Double longitud, Double latitud, String descLugar) throws RepositorioException, EntidadNoEncontrada;
	
	Producto recuperar(String id) throws RepositorioException, EntidadNoEncontrada;

	void borrar(String id) throws RepositorioException, EntidadNoEncontrada;

	List<Producto> listar() throws RepositorioException;
	//TODO: Luego cambiar a listar
	Page<ProductoDTO> getListadoPaginado(Pageable pageable);
	
}
