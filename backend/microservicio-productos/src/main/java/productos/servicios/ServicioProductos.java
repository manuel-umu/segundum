package productos.servicios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import productos.dto.ProductoResDTO;
import productos.enumerados.EnumEstado;
import productos.modelo.Categoria;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;
import productos.modelo.ProductoRes;
import productos.modelo.Usuario;
import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;
import productos.repositorios.RepositorioCategorias;
import productos.repositorios.RepositorioProductos;
import productos.repositorios.RepositorioUsuarios;

@Service
public class ServicioProductos implements IServicioProductos {

	@Autowired
	private RepositorioProductos productoRepo;

	@Autowired
	private RepositorioCategorias categoriaRepo;

	@Autowired
	private RepositorioUsuarios usuarioRepo;

	@Autowired
	public ServicioProductos(RepositorioProductos productoRepo, RepositorioCategorias categoriaRepo,
			RepositorioUsuarios usuarioRepo, IServicioCategorias servicioC) {
		this.productoRepo = productoRepo;
		this.categoriaRepo = categoriaRepo;
		this.usuarioRepo = usuarioRepo;
	}

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

	public Page<ProductoResDTO> getListadoPaginado(Pageable pageable) {
		return this.productoRepo.findAll(pageable).map(ProductoResDTO::toDto);
	}

	@Override
	public void ponerVendido(String id) throws RepositorioException, EntidadNoEncontrada {
		Producto p = productoRepo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontrada("Producto no encontrado: " + id));
		p.setVendido(true);
		productoRepo.save(p);
	}

	@Override
	public void añadirVisualizacion(String id) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		// Recuperamos el producto y sumamos uno a sus visualizaciones, updateamos
		// después
		Producto p = productoRepo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontrada("Producto no encontrado: " + id));
		p.setVisualizaciones(p.getVisualizaciones() + 1);
		productoRepo.save(p);
	}

	@Override
	public List<ProductoRes> historialMes(Integer mes, Integer year) throws RepositorioException {
		// Control de integridad de los datos
		if (mes == null || (mes < 1 || mes > 12))
			throw new IllegalArgumentException("mes: no debe ser nulo ni menor a 1 o mayor a 12");
		if (year == null)
			throw new IllegalArgumentException("año: no debe ser nulo");
		// Para este método tenemos que crear una clase Producto Resumen que nos ofrezca
		// unicamente los atributos que se nos pide
		// Primero recuperamos los productos con la fecha especificada
		List<Producto> resultados = productoRepo.getByFecha(mes, year);
		// Podriamos hacer lambdas para la ordenacion pero no podemos comparar int con
		// Integer
		// Así que utilizamos Comparator con el método getVisualizaciones() y ordenamos
		// de manera desendente con reversed()
		resultados.sort(Comparator.comparing(Producto::getVisualizaciones).reversed());

		// Ahora en otro stream a partir de .map pasamos los Productos a ProductosRes
		// cogiendo con los getters los atributos a incluir en el resumen
		List<ProductoRes> resultadosRes = resultados.stream().map(r -> new ProductoRes(r.getId(), r.getTitulo(),
				r.getPrecio(), r.getFechaPubli(), r.getCategoria().getNombre(), r.getVisualizaciones()))
				.collect(Collectors.toList());

		return resultadosRes;
	}

	@Override
	public Page<ProductoResDTO> productosDeUsuario(String idVendedor, Pageable pageable)
			throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (idVendedor == null || idVendedor.isEmpty())
			throw new IllegalArgumentException("idVendedor: no debe ser nulo ni vacio");

		// Comprobamos que el usuario existe para devolver un 404 si no es así
		usuarioRepo.findById(idVendedor)
				.orElseThrow(() -> new EntidadNoEncontrada("Usuario no encontrado: " + idVendedor));

		return productoRepo.getByVendedor(idVendedor, pageable).map(ProductoResDTO::toDto);
	}

	@Override
	public void modificarProducto(String id, Float precio, String descripcion)
			throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		// Recuperamos por la id el producto, comprobamos los campos de descripcion y
		// precio y updateamos
		Producto p = productoRepo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontrada("Producto no encontrado: " + id));
		if (precio != null && precio >= 0) {
			p.setPrecio(precio);
		}
		if (descripcion != null && !descripcion.isEmpty()) {
			p.setDescripcion(descripcion);
		}
		productoRepo.save(p);
	}
	
	@Override
	public void crearUsuario(String id, String nombre, String apellidos, String email) throws RepositorioException{
		// Control de integridad de los datos
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
		if (apellidos == null || apellidos.isEmpty())
			throw new IllegalArgumentException("apellidos: no debe ser nulo ni vacio");
		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");
		Usuario usuario = new Usuario(nombre, apellidos, email);
		usuario.setId(id);
		usuarioRepo.save(usuario);
	}
	
	@Override
	public void modificarUsuario(String id, String nombre, String apellidos) throws RepositorioException, EntidadNoEncontrada{
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Usuario usuario = usuarioRepo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontrada("Usuario no encontrado: " + id));
		if (nombre != null && !nombre.isEmpty()) {
			usuario.setNombre(nombre);
		}
		if (apellidos != null && !apellidos.isEmpty()) {
			usuario.setApellidos(apellidos);
		}
		usuarioRepo.save(usuario);
	}
}
