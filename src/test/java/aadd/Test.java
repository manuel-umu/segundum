package aadd;

import java.time.LocalDate;
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
		String idU = servicioU.registrarUsuario(nombre, apellidos, email, fecha, contra, telefono);
		System.out.println("Id1 = " + idU);
		Usuario recuperado = servicioU.getUsuario(idU);
		System.out.println("Usuario recuperado HU1 = " + recuperado.toString());
		String idU2 = servicioU.registrarUsuario(nombre, apellidos, email, fecha, contra, telefono);
		System.out.println("Id2 = " + idU2); // Debe salir null ya que se esta intentando registrar un usuario con un
												// email ya registrado
		// HU2
		String idHU1 = servicioU.registrarUsuario(nombre, apellidos, "alfonso@um.es", fecha, contra, telefono);
		Usuario recuperado2 = servicioU.getUsuario(idHU1);
		System.out.println("H2 - Antes = " + recuperado2.toString());
		servicioU.modificarUsuario(idHU1, "Jesus", "De Nazaret", contra, fecha, "111222333");
		Usuario recuperado3 = servicioU.getUsuario(idHU1);
		System.out.println("HU2 - Despues = " + recuperado3.toString());

		// HU3
		String idP = servicioP.altaProducto("Taladro", "Taladro eléctrico", 20f, EnumEstado.COMONUEVO, "11111", false,
				"9999");
		System.out.println("ID del producto dado de alta: " + idP);

		// HU4
		Usuario u1 = new Usuario();
		Categoria c1 = new Categoria();
		Producto producto = new Producto("Taladro", "Taladro eléctrico", 20f, EnumEstado.COMONUEVO, c1, false, u1);
		servicioP.modificarProducto(idP, 10f, "Taladro eléctrico con garantía");
		System.out.println(
				"Descripcion nueva: " + producto.getDescripcion() + " y precio nuevo: " + producto.getPrecio());

		// HU5
		servicioP.asignarRecogida(idP, 15.0, 16.1, "Biblioteca regional");
		System.out.println("Nuevo lugar de recogida: " + producto.getRecogida().getDescripcion());

		// HU6
		List<ProductoRes> productosDiciembre = servicioP.historialMes(12, 2021);
		for (ProductoRes productoRes : productosDiciembre) {
			System.out.println(productoRes.getTitulo() + productoRes.getPrecio() + productoRes.getVisualizaciones()
					+ productoRes.getCategoria() + productoRes.getFechaPubli());
		}

		// HU7
		List<Producto> productos1 = servicioP.buscarProductos(null, null, null, null);
		for (Producto p : productos1) {
			System.out.println(p.getId());
		}

		List<Producto> productos2 = servicioP.buscarProductos(c1.getId(), null, null, null);
		for (Producto p : productos2) {
			System.out.println(p.getId());
		}

		List<Producto> productos3 = servicioP.buscarProductos(c1.getId(), "hola", null, null);
		for (Producto p : productos3) {
			System.out.println(p.getId());
		}

		List<Producto> productos4 = servicioP.buscarProductos(c1.getId(), "hola", EnumEstado.ACEPTABLE, null);
		for (Producto p : productos4) {
			System.out.println(p.getId());
		}

		List<Producto> productos5 = servicioP.buscarProductos(c1.getId(), "hola", EnumEstado.ACEPTABLE, 50f);
		for (Producto p : productos5) {
			System.out.println(p.getId());
		}

		// HU8
		servicioC.cargarCategoria(c1.getId());
		System.out.println("ID de la categoría añadida " + servicioC.recuperarDescCategoria(c1.getId()));

		// HU9
		servicioC.modificarCategoria(c1.getId(), "Nueva descripción");
		System.out.println("Nueva descripción " + servicioC.recuperarDescCategoria(c1.getId()));

		// Pruebas adicionales del paquete servicios no probados en las HU:

	}
}
