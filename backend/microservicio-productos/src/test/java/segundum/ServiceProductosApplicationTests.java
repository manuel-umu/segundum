package segundum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import segundum.enumerados.EnumEstado;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import segundum.modelo.Categoria;
import segundum.modelo.Producto;
import segundum.repositorios.RepositorioException;
import segundum.servicios.IServicioCategorias;
import segundum.servicios.IServicioProductos;

// IMPORTANTE: Antes de ejecutar el test, asegurarse de que existe un usuario asi (solo tiene q coincidir la id):
/*
 INSERT INTO usuario (id, nombre, apellidos, email) 
VALUES (
    'idVendedor123', 
    'Pedro', 
    'Pedrito', 
    'gmail@gmail.com'
);
 */
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ServiceProductosApplicationTests {
	@Autowired
	private IServicioCategorias servicioC;

	@Autowired
	private IServicioProductos servicioP;

	// --- Datos de prueba ---
	private String idVendedor = "idVendedor123";
	private String p1;
	private Categoria categoria;

	@BeforeAll
	void cargarCategoria() throws RepositorioException {
		servicioC.cargarCategoria("src/test/java/segundum/categorias/Bricolaje.xml");
		LinkedList<Categoria> categorias = servicioC.recuperarCategoriaRaiz();
		assertFalse(categorias.isEmpty(), "Debería haber categorías cargadas");
		categoria = categorias.getFirst();
	}

	@Test
	void testAltaProducto() throws Exception {
		String idP = servicioP.crear("Taladro", "Taladro eléctrico", 20f, EnumEstado.COMONUEVO, categoria.getId(),
				false, idVendedor);
		assertNotNull(idP);
		Producto recuperadoP = servicioP.recuperar(idP);
		assertEquals("Taladro", recuperadoP.getTitulo());
	}

	@Test
	void testModificarProductoYAsignarRecogida() throws Exception {
		p1 = servicioP.crear("Destornillador", "En buenisimo estado", 20f, EnumEstado.PARAPIEZAS_O_REPARAR,
				categoria.getId(), false, idVendedor);

		servicioP.actualizar(p1, 2f, "Alomejor no esta tan bien");
		Producto recuperadoActualizado = servicioP.recuperar(p1);
		assertEquals(2f, recuperadoActualizado.getPrecio());
		
		servicioP.asignarRecogida(p1, 15.0, 16.1, "Biblioteca regional");
		Producto recuperadoRecogida = servicioP.recuperar(p1);
		assertEquals("Biblioteca regional", recuperadoRecogida.getRecogida().getDescripcion());
	}

	@Test
	void testListar() throws Exception {
		fail();
		// TODO
	}

	@Test
	void testBorrarProducto() throws Exception {
		fail();
		// TODO
	}
}
