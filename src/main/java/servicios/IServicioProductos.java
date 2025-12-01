package servicios;

import java.util.List;

import enumerados.EnumEstado;
import modelo.LugarRecogida;
import modelo.Producto;
import modelo.ProductoRes;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public interface IServicioProductos {
	
	public String altaProducto(String titulo, String descripcion, Float precio, EnumEstado estado, String idCategoria, Boolean envioDispo, String idVendedor, LugarRecogida lg) throws RepositorioException, EntidadNoEncontrada;
	
	public void asignarRecogida(String id, Double longitud, Double latitud, String descLugar) throws RepositorioException, EntidadNoEncontrada;
	
	public void modificarProducto(String id, Float precio, String descripcion) throws RepositorioException, EntidadNoEncontrada;

	public void añadirVisualizacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	public List<ProductoRes> historialMes(Integer mes, Integer año) throws RepositorioException;
	
	public List<Producto> buscarProductos(String idCategoria, String texto, EnumEstado estado, Float precioMax) throws RepositorioException, EntidadNoEncontrada;

	public Producto getProducto(String id) throws RepositorioException, EntidadNoEncontrada;
}
