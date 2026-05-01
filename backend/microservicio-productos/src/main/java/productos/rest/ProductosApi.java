package productos.rest;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import productos.dto.LugarRecogidaDTO;
import productos.dto.ProductoDTO;
import productos.modelo.ProductoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//TODO TERMINAR APIRESPONSES
@Tag(name = "Productos", description = "API para la gestión de productos")
public interface ProductosApi {

	@Operation(summary = "Crear un producto", description = "Da de alta un nuevo producto en el sistema")
	@ApiResponse(responseCode = "200", description = "Producto creado correctamente. La URI del nuevo recurso se devuelve en la cabecera Location.")
	public ResponseEntity<Void> createProducto(@Valid @RequestBody ProductoDTO p, HttpServletRequest request)
			throws Exception;

	@Operation(summary = "Obtener producto", description = "Obtiene un producto por su id")
	@ApiResponse(responseCode = "200", description = "Producto encontrado y devuelto correctamente.")
	public EntityModel<ProductoDTO> getProducto(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id)
			throws Exception;

	@Operation(summary = "Listado de productos", description = "Obtiene un listado paginado de productos")
	@ApiResponse(responseCode = "200", description = "Listado devuelto correctamente.")
	public PagedModel<EntityModel<ProductoDTO>> getProductos(@RequestParam int page, @RequestParam int size)
			throws Exception;

	@Operation(summary = "Asignar lugar de recogida", description = "Asigna un lugar de recogida a un producto existente")
	@ApiResponse(responseCode = "200", description = "Lugar de recogida asignado correctamente.")
	public ResponseEntity<Void> asignRecogida(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id,
			@RequestBody LugarRecogidaDTO recogida, HttpServletRequest request) throws Exception;

	@Operation(summary = "Registrar visualización", description = "Incrementa en uno el contador de visualizaciones de un producto.")
	@ApiResponse(responseCode = "200", description = "Visualización registrada correctamente.")
	public ResponseEntity<Void> addVisualizacion(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id)
			throws Exception;

	@Operation(summary = "Historial de productos por mes", description = "Devuelve la lista de productos publicados en un mes y año concretos.")
	@ApiResponse(responseCode = "200", description = "Historial devuelto correctamente.")
	public ResponseEntity<List<ProductoRes>> getHistorial(
			@Parameter(description = "Año del historial", required = true, example = "2024") @PathVariable Integer year,
			@Parameter(description = "Mes del historial (1-12)", required = true, example = "3") @PathVariable Integer mes)
			throws Exception;

	@Operation(summary = "Modificar producto", description = "Actualiza un producto (precio y descripción)")
	@ApiResponse(responseCode = "200", description = "Producto modificado correctamente.")
	public ResponseEntity<Void> modificarProducto(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id,
			@RequestBody ProductoDTO p, HttpServletRequest request) throws Exception;

}
