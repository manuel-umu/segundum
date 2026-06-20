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
import productos.dto.ProductoResDTO;
import productos.enumerados.EnumEstado;
import productos.modelo.ProductoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Productos", description = "API para la gestión de productos")
public interface ProductosApi {

	@Operation(summary = "Crear un producto", description = "Da de alta un nuevo producto en el sistema")
	@ApiResponse(responseCode = "200", description = "Producto creado correctamente. La URI del nuevo recurso se devuelve en la cabecera Location.")
	public ResponseEntity<Void> createProducto(
			@Parameter(description = "Datos del producto a crear", required = true) @Valid @RequestBody ProductoDTO p,
			HttpServletRequest request) throws Exception;

	@Operation(summary = "Obtener producto", description = "Obtiene un producto por su id")
	@ApiResponse(responseCode = "200", description = "Producto encontrado y devuelto correctamente.")
	public EntityModel<ProductoResDTO> getProducto(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id)
			throws Exception;

	@Operation(summary = "Listado de productos", description = "Obtiene un listado paginado de productos")
	@ApiResponse(responseCode = "200", description = "Listado devuelto correctamente.")
	public PagedModel<EntityModel<ProductoResDTO>> getProductos(
			@Parameter(description = "Número de página (empieza en 0)", example = "0") @RequestParam int page,
			@Parameter(description = "Tamaño de página", example = "10") @RequestParam int size) throws Exception;

	@Operation(summary = "Listado de productos en venta", description = "Obtiene un listado paginado de productos no vendidos. Permite filtrar (todos opcionales) por categoria (incluyendo descendientes), texto en la descripcion, estado (igual o mejor) y precio maximo.")
	@ApiResponse(responseCode = "200", description = "Listado devuelto correctamente.")
	public PagedModel<EntityModel<ProductoResDTO>> getProductosEnVenta(
			@Parameter(description = "Identificador de la categoria") @RequestParam(required = false) String categoria,
			@Parameter(description = "Texto contenido en la descripcion") @RequestParam(required = false) String texto,
			@Parameter(description = "Estado minimo aceptado (igual o mejor)") @RequestParam(required = false) EnumEstado estado,
			@Parameter(description = "Precio maximo") @RequestParam(required = false) Float precioMax,
			@Parameter(description = "Número de página (empieza en 0)", example = "0") @RequestParam int page,
			@Parameter(description = "Tamaño de página", example = "10") @RequestParam int size) throws Exception;

	@Operation(summary = "Asignar lugar de recogida", description = "Asigna un lugar de recogida a un producto existente")
	@ApiResponse(responseCode = "200", description = "Lugar de recogida asignado correctamente.")
	public ResponseEntity<Void> asignRecogida(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id,
			@Parameter(description = "Datos del lugar de recogida", required = true) @RequestBody LugarRecogidaDTO recogida,
			HttpServletRequest request) throws Exception;

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

	@Operation(summary = "Productos de un usuario", description = "Devuelve un listado paginado de los productos publicados por un usuario (vendedor) concreto.")
	@ApiResponse(responseCode = "200", description = "Listado de productos del usuario devuelto correctamente.")
	public ResponseEntity<PagedModel<EntityModel<ProductoResDTO>>> getProductosUsuario(
			@Parameter(description = "Identificador del usuario vendedor", required = true) @PathVariable String idUsuario,
			@Parameter(description = "Número de página (empieza en 0)", example = "0") @RequestParam int page,
			@Parameter(description = "Tamaño de página", example = "10") @RequestParam int size,
			HttpServletRequest request) throws Exception;

	@Operation(summary = "Modificar producto", description = "Actualiza un producto (precio y descripción)")
	@ApiResponse(responseCode = "200", description = "Producto modificado correctamente.")
	public ResponseEntity<Void> modificarProducto(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id,
			@Parameter(description = "Campos a modificar del producto") @RequestBody ProductoDTO p,
			HttpServletRequest request) throws Exception;

	@Operation(summary = "Eliminar producto", description = "Elimina un producto. Solo puede hacerlo el vendedor propietario.")
	@ApiResponse(responseCode = "204", description = "Producto eliminado correctamente.")
	@ApiResponse(responseCode = "403", description = "El usuario no es el propietario del producto.")
	public ResponseEntity<Void> deleteProducto(
			@Parameter(description = "Identificador del producto", required = true) @PathVariable String id,
			HttpServletRequest request) throws Exception;

}
