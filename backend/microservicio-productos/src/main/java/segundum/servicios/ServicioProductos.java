package segundum.servicios;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import segundum.enumerados.EnumEstado;
import segundum.modelo.Categoria;
import segundum.modelo.LugarRecogida;
import segundum.modelo.Producto;
import segundum.modelo.Usuario;
import segundum.repositorios.EntidadNoEncontrada;
import segundum.repositorios.RepositorioException;
import segundum.repositorios.RepositorioCategorias;
import segundum.repositorios.RepositorioProductos;
import segundum.repositorios.RepositorioUsuarios;

@Service
public class ServicioProductos implements IServicioProductos {

	@Autowired
	private RepositorioProductos productoRepo;

	@Autowired
	private RepositorioCategorias categoriaRepo;

	@Autowired
	private RepositorioUsuarios usuarioRepo;

	@Autowired
	private IServicioCategorias servicioC;

	@Override
	public String crear(String titulo, String descripcion, Float precio, EnumEstado estado, String idCategoria,
			Boolean envioDispo, String idVendedor) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (titulo == null || titulo.isEmpty())
			throw new IllegalArgumentException("titulo: no debe ser nulo ni vacio");
		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("descripcion: no debe ser nulo ni vacio");
		if (precio == null)
			throw new IllegalArgumentException("precio: no debe ser nulo ni vacio");
		if (estado == null)
			throw new IllegalArgumentException("estado: no debe ser nulo ni vacio");
		if (idCategoria == null || idCategoria.isEmpty())
			throw new IllegalArgumentException("idCategoria: no debe ser nulo ni vacio");
		if (envioDispo == null)
			throw new IllegalArgumentException("envioDispo: no debe ser nulo ni vacio");
		if (idVendedor == null || idVendedor.isEmpty())
			throw new IllegalArgumentException("idVendedor: no debe ser nulo ni vacio");

		// Necesitamos realizar 2 consultas en los repos a partir de los ids dados para
		// construir el producto
		Categoria categoria = categoriaRepo.findById(idCategoria)
				.orElseThrow(() -> new EntidadNoEncontrada("Categoria no encontrada: " + idCategoria));
		Usuario usuario = usuarioRepo.findById(idVendedor)
				.orElseThrow(() -> new EntidadNoEncontrada("Usuario no encontrado: " + idVendedor));
		Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, envioDispo, usuario);

		// Save nos devuelve la entidad persistida
		Producto p = productoRepo.save(producto);
		return p.getId();
	}

	@Override
	public void asignarRecogida(String id, Double longitud, Double latitud, String descLugar)
			throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if (longitud == null)
			throw new IllegalArgumentException("longitud: no debe ser nulo ni vacio");
		if (latitud == null)
			throw new IllegalArgumentException("latitud: no debe ser nulo ni vacio");
		if (descLugar == null || descLugar.isEmpty())
			throw new IllegalArgumentException("descLugar: no debe ser nulo ni vacio");

		// Construimos el LugarRecogida
		LugarRecogida lugar = new LugarRecogida(descLugar, longitud, latitud);
		// Recuperamos por la id el producto, añadimos el lugar y updateamos en el repo
		Producto producto = productoRepo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontrada("Producto no encontrado: " + id));
		producto.setRecogida(lugar);
		productoRepo.save(producto);
	}

	@Override
	public void actualizar(String id, Float precio, String descripcion)
			throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if (precio == null && (descripcion == null || descripcion.isEmpty()))
			throw new IllegalArgumentException("precio o descripcion: no debe ser nulo ni vacio");

		// Recuperamos por la id el producto, comprobamos los campos de descripcion y
		// precio y updateamos
		Producto producto = productoRepo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontrada("Producto no encontrado: " + id));
		if (precio != null && precio >= 0) {
			producto.setPrecio(precio);
		}
		if (descripcion != null) {
			producto.setDescripcion(descripcion);
		}
		productoRepo.save(producto);
	}

	@Override
	public Producto recuperar(String id) throws RepositorioException, EntidadNoEncontrada {
		return productoRepo.findById(id).orElseThrow(() -> new EntidadNoEncontrada("Producto no encontrado: " + id));
	}

	@Override
	public void borrar(String id) throws RepositorioException, EntidadNoEncontrada {
		Producto u = recuperar(id);
		productoRepo.delete(u);
	}

	@Override
	public List<Producto> listar() throws RepositorioException {
		return productoRepo.findAll();
	}

}
