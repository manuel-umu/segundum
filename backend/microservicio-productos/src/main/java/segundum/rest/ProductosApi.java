package segundum.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import segundum.dto.LugarRecogidaDTO;
import segundum.dto.ProductoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

// TODO: Completar. Esto es la base para openapi
public interface ProductosApi {
	@Operation(summary = "Crear un producto", description = "Da de alta un nuevo producto en el sistema")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createProducto(@Valid @RequestBody ProductoDTO nuevaEncuesta) throws Exception;

	@Operation(summary = "Obtener producto", description = "Obtiene un producto por su id")
	@GetMapping("/{id}")
	public EntityModel<ProductoDTO> getProducto(@Parameter(description = "ID del producto") @PathVariable String id)
			throws Exception;

	@Operation(summary = "Listado de productos", description = "Obtiene un listado paginado de productos")
	@GetMapping
	public PagedModel<EntityModel<ProductoDTO>> getProductosPaginado(Pageable paginacion) throws Exception;

	@Operation(summary = "Asignar lugar de recogida", description = "Asigna un lugar de recogida a un producto existente")
	@PutMapping(value = "/{id}/recogida", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> asignRecogida(@PathVariable String id, @Valid @RequestBody LugarRecogidaDTO recogida)
			throws Exception;
}
