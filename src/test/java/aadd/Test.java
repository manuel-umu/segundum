package aadd;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoRes;
import modelo.Usuario;
import enumerados.EnumEstado;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;
import servicios.IServicioCategorias;
import servicios.IServicioProductos;

public class Test {

	public static void main(String[] args) throws Exception {
		IServicioUsuarios servicioU = FactoriaServicios.getServicio(IServicioUsuarios.class);
		IServicioCategorias servicioC = FactoriaServicios.getServicio(IServicioCategorias.class);
		IServicioProductos servicioP = FactoriaServicios.getServicio(IServicioProductos.class);
		String nombre = "Alphonse";
		String apellidos = "Sprinfield um";
		String email = "gmail@gmail.com";
		LocalDate fecha = LocalDate.of(2000, 12, 1);
		String contra = "hola1234";
		String telefono = "666777888";
		
		// HU1
		System.out.println("\n=================== HU1 ===================\n");
		String idU = servicioU.registrarUsuario(nombre, apellidos, email, fecha, contra, telefono);
		System.out.println("Id1 = " + idU);
		Usuario recuperado = servicioU.getUsuario(idU);
		System.out.println("Usuario recuperado = " + recuperado.toString());
		String idU2 = servicioU.registrarUsuario(nombre, apellidos, email, fecha, contra, telefono);
		System.out.println("Id2 (deberia ser null) = " + idU2); // Debe salir null ya que se esta intentando registrar un usuario con un
												// email ya registrado
		// HU2
		System.out.println("\n=================== HU2 ===================\n");
		String idHU1 = servicioU.registrarUsuario(nombre, apellidos, "alfonso@um.es", fecha, contra, telefono);
		Usuario recuperado2 = servicioU.getUsuario(idHU1);
		System.out.println("Antes = " + recuperado2.toString());
		servicioU.modificarUsuario(idHU1, "Jesus", "De Nazaret", "contraActualizada", LocalDate.of(1, 1, 1), "111222333");
		Usuario recuperado3 = servicioU.getUsuario(idHU1);
		System.out.println("Despues = " + recuperado3.toString());

		// HU3
		System.out.println("\n=================== HU3 ===================\n");
		String idVendedor = idHU1;
		servicioC.cargarCategoria("src/main/java/categorias/Bricolaje.xml");
		LinkedList<Categoria> categorias = servicioC.recuperarCategoriaRaiz();
		Categoria c1 = categorias.getFirst();
		System.out.println("ID Categoria 1 = " + c1.getId());
		String idP = servicioP.altaProducto("Taladro", "Taladro eléctrico", 20f, EnumEstado.COMONUEVO, c1.getId(), false,
				idVendedor);
		System.out.println("ID Producto 1 = " + idP);
		Producto recuperadoP = servicioP.getProducto(idP);
		System.out.println("Producto dado de alta = " + recuperadoP.toString());

		// HU4
		System.out.println("\n=================== HU4 ===================\n");
		String idH4 = servicioP.altaProducto("Destornillador", "En buenisimo estado", 20f, EnumEstado.PARAPIEZAS, c1.getId(), false,
				idVendedor);
		Producto recuperadoH4 = servicioP.getProducto(idH4);
		System.out.println("Producto original. Descripcion = \"" + recuperadoH4.getDescripcion() + "\" y precio = " + recuperadoH4.getPrecio());
		servicioP.modificarProducto(idH4, 2f, "Alomejor no esta tan bien");
		Producto recuperadoH4_2 = servicioP.getProducto(idH4);
		System.out.println("Producto modificado. Descripcion = \"" + recuperadoH4_2.getDescripcion() + "\" y precio nuevo = " + recuperadoH4_2.getPrecio());

		// HU5
		System.out.println("\n=================== HU5 ===================\n");
		servicioP.asignarRecogida(idH4, 15.0, 16.1, "Biblioteca regional");
		Producto recuperadoH5 = servicioP.getProducto(idH4);
		System.out.println("Nuevo lugar de recogida: " + recuperadoH5.getRecogida().getDescripcion());

		// HU6
		System.out.println("\n=================== HU6 ===================\n");
		servicioP.añadirVisualizacion(idH4);
		List<ProductoRes> productosDiciembre = servicioP.historialMes(11, 2025);
		for (ProductoRes productoRes : productosDiciembre) {
			System.out.println(productoRes.toString());
		}

		// HU7
		System.out.println("\n=================== HU7 ===================\n");
		servicioC.cargarCategoria("src/main/java/categorias/Electronica.xml");
		LinkedList<Categoria> categorias2 = servicioC.recuperarCategoriaRaiz();
		Categoria c2 = categorias2.getFirst();
		String idPH71 = servicioP.altaProducto("Samsung Galaxy S28", "Sin abrir", 20f, EnumEstado.NUEVO, c2.getId(), false,
				idVendedor);
		String idPH72 = servicioP.altaProducto("Samsung Galaxy S29", "Roto", 20f, EnumEstado.REPARAR, c2.getId(), false,
				idVendedor);
		String idPH73 = servicioP.altaProducto("Samsung Galaxy S30", "Casi nuevo", 20f, EnumEstado.COMONUEVO, c2.getId(), false,
				idVendedor);
		String idPH74 = servicioP.altaProducto("Samsung Galaxy S31", "Medio medio", 20f, EnumEstado.ACEPTABLE, c2.getId(), false,
				idVendedor);
		List<Producto> productos1 = servicioP.buscarProductos(null, null, null, null);
		System.out.println("Resultados búsqueda 1:");
		for (Producto p : productos1) {
			System.out.println("ID: " + p.getId() + ". Título: " + p.getTitulo());
		}

		List<Producto> productos2 = servicioP.buscarProductos(c2.getId(), null, null, null);
		System.out.println();
		System.out.println("Resultados búsqueda 2:");
		for (Producto p : productos2) {
			System.out.println("ID: " + p.getId() + ". Título: " + p.getTitulo());
		}

		List<Producto> productos3 = servicioP.buscarProductos(c2.getId(), "Sin abrir", null, null);
		System.out.println();
		System.out.println("Resultados búsqueda 3:");
		for (Producto p : productos3) {
			System.out.println("ID: " + p.getId() + ". Título: " + p.getTitulo());
		}

		List<Producto> productos4 = servicioP.buscarProductos(c2.getId(), "Casi nuevo", EnumEstado.ACEPTABLE, null);
		System.out.println();
		System.out.println("Resultados búsqueda 4:");
		for (Producto p : productos4) {
			System.out.println("ID: " + p.getId() + ". Título: " + p.getTitulo());
		}

		List<Producto> productos5 = servicioP.buscarProductos(c2.getId(), "Casi nuevo", EnumEstado.ACEPTABLE, 10f);
		System.out.println();
		System.out.println("Resultados búsqueda 5:");
		for (Producto p : productos5) {
			System.out.println("ID: " + p.getId() + ". Título: " + p.getTitulo());
		}

		// HU8
		System.out.println("\n=================== HU8 ===================\n");
		servicioC.cargarCategoria("src/main/java/categorias/Mobiliario.xml");
		System.out.println("Nuevas categorías añadidas: " + servicioC.getCategoria("6369") + servicioC.recuperarDescCategoria("6369"));

		// HU9
		System.out.println("\n=================== HU9 ===================\n");
		servicioC.modificarCategoria(c1.getId(), "Nueva descripción");
		System.out.println("Nueva descripción: " + servicioC.getCategoria(c1.getId()));

		// Pruebas adicionales del paquete servicios no probados en las HU:

	}
}
