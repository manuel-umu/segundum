package servicios;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import enumerados.EnumEstado;
import modelo.Categoria;
import modelo.LugarRecogida;
import modelo.Producto;
import modelo.ProductoRes;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import repositorios.especificos.RepositorioCategoriasAdHoc;
import repositorios.especificos.RepositorioProductosAdHoc;
import repositorios.especificos.RepositorioUsuariosAdHoc;

public class ServicioProductos implements IServicioProductos {
	private RepositorioProductosAdHoc productoRepo = FactoriaRepositorios.getRepositorio(Producto.class);
	private RepositorioCategoriasAdHoc categoriaRepo = FactoriaRepositorios.getRepositorio(Categoria.class);
	private RepositorioUsuariosAdHoc usuarioRepo = FactoriaRepositorios.getRepositorio(Usuario.class);
	private ServicioCategorias servicioC = new ServicioCategorias();

	@Override
	public String altaProducto(String titulo, String descripcion, Float precio, EnumEstado estado, String idCategoria,
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
		Categoria categoria = categoriaRepo.getById(idCategoria);
		Usuario usuario = usuarioRepo.getById(idVendedor);
		Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, envioDispo, usuario);

		// Add nos devuelve el id del producto una vez lo damos de "alta"
		return productoRepo.add(producto);
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
		Producto producto = productoRepo.getById(id);
		producto.setRecogida(lugar);
		productoRepo.update(producto);
	}

	@Override
	public void modificarProducto(String id, Float precio, String descripcion)
			throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if (precio == null && (descripcion == null || descripcion.isEmpty()))
			throw new IllegalArgumentException("precio o descripcion: no debe ser nulo ni vacio");

		// Recuperamos por la id el producto, comprobamos los campos de descripcion y
		// precio y updateamos
		Producto producto = productoRepo.getById(id);
		if (precio != null && precio >= 0) {
			producto.setPrecio(precio);
		}
		if (descripcion != null) {
			producto.setDescripcion(descripcion);
		}
		productoRepo.update(producto);
	}

	@Override
	public void añadirVisualizacion(String id) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		// Recuperamos el producto y sumamos uno a sus visualizaciones, updateamos
		// después
		Producto producto = productoRepo.getById(id);
		producto.setVisualizaciones(producto.getVisualizaciones() + 1);
		productoRepo.update(producto);
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
		List<ProductoRes> resultadosRes = resultados.stream().map(r -> new ProductoRes(r.getId(), r.getTitulo(), r.getPrecio(),
				r.getFechaPubli(), r.getCategoria().getNombre(), r.getVisualizaciones())).collect(Collectors.toList());

		return resultadosRes;
	}

	@Override
	public List<Producto> buscarProductos(String idCategoria, String texto, EnumEstado estado, Float precioMax)
			throws RepositorioException, EntidadNoEncontrada {
		// Obtenemos todos los productos del repositorio
		List<Producto> productos;
		// Hagamos un analisis de casos para la búsqueda
		if (idCategoria == null || idCategoria.isEmpty()) {
			// Aquí no hay tratamiento de integridad ya que si es nulo la lista de productos
			// son todos los productos del repositorio
			productos = productoRepo.getAll();
		} else {
			// En el caso de que se especifique hacemos uso de ServicioCategorias para poder
			// llamar a la función recurrente que lista hijos de categorías
			LinkedList<Categoria> categorias = servicioC.recuperarDescCategoria(idCategoria);
			
			productos = productoRepo.getByCategorias(idCategoria, categorias);
		}

		// Ahora a partir de nuestra lista vamos cribando los demás parámetros
		// dependiendo si estos son vacíos o no
		// Si son vacíos dejamos la lista como está (hay que incluir todos) de lo
		// contrario vamos filtrando
		if (texto != null) {
			productos = productos.stream().filter(p -> p.getDescripcion().contains(texto)).collect(Collectors.toList());
		}

		// Utilizamos ordinal() para saber de que enumerado se trata, los hemos puesto
		// de mejor a peor así que 0 = NUEVO, 5 = PARAPIEZAS_O_REPARAR
		if (estado != null) {
			productos = productos.stream().filter(p -> p.getEstado().ordinal() <= estado.ordinal())
					.collect(Collectors.toList());
		}

		// Nos quedamos con los productos con menos coste de lo introducido
		if (precioMax != null) {
			productos = productos.stream().filter(p -> p.getPrecio() <= precioMax).collect(Collectors.toList());
		}

		return productos;
	}
	
	@Override
	public Producto getProducto(String id) throws RepositorioException, EntidadNoEncontrada {
		return productoRepo.getById(id);
	}
}
