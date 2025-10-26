package servicios;

import java.util.LinkedList;

import enumerados.EnumEstado;
import modelo.Categoria;
import modelo.LugarRecogida;
import modelo.Producto;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.Repositorio;
import repositorios.RepositorioException;

public class ServicioProductos implements IServicioProductos {
	private Repositorio<Producto, String> productoRepo = FactoriaRepositorios.getRepositorio(Producto.class);
	private Repositorio<Categoria, String> categoriaRepo = FactoriaRepositorios.getRepositorio(Categoria.class);
	private Repositorio<Usuario, String> usuarioRepo = FactoriaRepositorios.getRepositorio(Usuario.class);

	@Override
	public String altaProducto(String titulo, String descripcion, Float precio, EnumEstado estado, String idCategoria,
			Boolean envioDispo, String idVendedor) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if(titulo == null || titulo.isEmpty())
			throw new IllegalArgumentException("titulo: no debe ser nulo ni vacio");
		if(descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("descripcion: no debe ser nulo ni vacio");
		if(precio == null)
			throw new IllegalArgumentException("precio: no debe ser nulo ni vacio");
		if(estado == null)
			throw new IllegalArgumentException("estado: no debe ser nulo ni vacio");
		if(idCategoria == null || idCategoria.isEmpty())
			throw new IllegalArgumentException("idCategoria: no debe ser nulo ni vacio");
		if(envioDispo == null)
			throw new IllegalArgumentException("envioDispo: no debe ser nulo ni vacio");
		if(idVendedor == null || idVendedor.isEmpty())
			throw new IllegalArgumentException("idVendedor: no debe ser nulo ni vacio");
		
		// Necesitamos realizar 2 consultas en los repos a partir de los ids dados para construir el producto
		Categoria categoria = categoriaRepo.getById(idCategoria);
		Usuario usuario = usuarioRepo.getById(idVendedor);
		Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, envioDispo, usuario);
		
		// Add nos devuelve el id del producto una vez lo damos de "alta"
		return productoRepo.add(producto);
	}

	@Override
	public void asignarRecogida(String id, Double longitud, Double latitud, String descLugar) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if(id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if(longitud == null)
			throw new IllegalArgumentException("longitud: no debe ser nulo ni vacio");
		if(latitud == null)
			throw new IllegalArgumentException("latitud: no debe ser nulo ni vacio");
		if(descLugar == null || descLugar.isEmpty())
			throw new IllegalArgumentException("descLugar: no debe ser nulo ni vacio");
		
		// Construimos el LugarRecogida
		LugarRecogida lugar = new LugarRecogida(descLugar, longitud, latitud);
		// Recuperamos por la id el producto, añadimos el lugar y updateamos en el repo
		Producto producto = productoRepo.getById(id);
		producto.setRecogida(lugar);
		productoRepo.update(producto);		
	}

	@Override
	public void modificarProducto(String id, Float precio, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if(id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if(precio == null && (descripcion == null || descripcion.isEmpty()))
			throw new IllegalArgumentException("precio o descripcion: no debe ser nulo ni vacio");
		
		// Recuperamos por la id el producto, comprobamos los campos de descripcion y precio y updateamos
		Producto producto = productoRepo.getById(id);
		if(precio != null && precio >= 0) {
			producto.setPrecio(precio);
		}
		if(descripcion != null) {
			producto.setDescripcion(descripcion);
		}
		productoRepo.update(producto);		
	}

	@Override
	public void añadirVisualizacion(String id) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if(id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Producto producto = productoRepo.getById(id);
		producto.setVisualizaciones(producto.getVisualizaciones() + 1);
		productoRepo.update(producto);
	}

	@Override
	public LinkedList<Producto> historialMes(Integer mes, Integer año) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Producto> buscarProductos(String idCategoria, String texto, EnumEstado estado, Float precioMax) {
		// TODO Auto-generated method stub
		return null;
	}

}
