package compraventas.rest;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import compraventas.dto.CompraventaInputDTO;
import compraventas.dto.CompraventaOutputDTO;
import compraventas.servicios.IServicioCompraventas;
import compraventas.servicios.ServicioCompraventas;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;

@Tag(name = "Compraventas", description = "API para la gestión de compraventas")
@RestController
@RequestMapping("/api/compraventas")
public class ControladorCompraventas {
	@Autowired
	private IServicioCompraventas servicio;

	@Autowired
	private PagedResourcesAssembler<CompraventaOutputDTO> pagedResourcesAssembler;

	public ControladorCompraventas(ServicioCompraventas servicio) {
		this.servicio = servicio;
	}

	@Operation(summary = "Registrar una compraventa", description = "El comprador adquiere un producto")
	@ApiResponse(responseCode = "201", description = "Compraventa registrada. Devuelve la URL del nuevo recurso.")
	@ApiResponse(responseCode = "403", description = "El usuario autenticado no coincide con el comprador.")
	@PostMapping
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<Void> comprarProducto(
			@Parameter(description = "Datos de la compraventa", required = true) @RequestBody CompraventaInputDTO input,
			HttpServletRequest request) throws IOException {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(input.getIdComprador())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		String id = servicio.registrarCompraventa(input.getIdProducto(), input.getIdComprador());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	@Operation(summary = "Listar compras de un usuario", description = "Devuelve las compraventas en las que el usuario es comprador")
	@ApiResponse(responseCode = "200", description = "Listado de compras devuelto correctamente.")
	@ApiResponse(responseCode = "403", description = "El usuario autenticado no coincide con el solicitado.")
	@GetMapping("/compras/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<PagedModel<EntityModel<CompraventaOutputDTO>>> obtenerCompras(
			@Parameter(description = "Identificador del usuario comprador", required = true) @PathVariable String idUsuario,
			@Parameter(description = "Número de página", required = true) @RequestParam int page,
			@Parameter(description = "Tamaño de página", required = true) @RequestParam int size,
			HttpServletRequest request) {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		Pageable pageable = PageRequest.of(page, size);
		Page<CompraventaOutputDTO> resultado = servicio.recuperarCompras(idUsuario, pageable);
		return ResponseEntity.ok(pagedResourcesAssembler.toModel(resultado));
	}

	@Operation(summary = "Listar ventas de un usuario", description = "Devuelve las compraventas en las que el usuario es vendedor")
	@ApiResponse(responseCode = "200", description = "Listado de ventas devuelto correctamente.")
	@ApiResponse(responseCode = "403", description = "El usuario autenticado no coincide con el solicitado.")
	@GetMapping("/ventas/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<PagedModel<EntityModel<CompraventaOutputDTO>>> obtenerVentas(
			@Parameter(description = "Identificador del usuario vendedor", required = true) @PathVariable String idUsuario,
			@Parameter(description = "Número de página", required = true) @RequestParam int page,
			@Parameter(description = "Tamaño de página", required = true) @RequestParam int size,
			HttpServletRequest request) {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		Pageable pageable = PageRequest.of(page, size);
		Page<CompraventaOutputDTO> resultado = servicio.recuperarVentas(idUsuario, pageable);
		return ResponseEntity.ok(pagedResourcesAssembler.toModel(resultado));
	}

	@Operation(summary = "Listar compraventas entre dos usuarios", description = "Solo accesible por administradores. Devuelve las compraventas entre un comprador y un vendedor concretos.")
	@ApiResponse(responseCode = "200", description = "Listado devuelto correctamente.")
	@GetMapping
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public PagedModel<EntityModel<CompraventaOutputDTO>> obtenerCompraventas(
			@Parameter(description = "Identificador del comprador", required = true) @RequestParam String idComprador,
			@Parameter(description = "Identificador del vendedor", required = true) @RequestParam String idVendedor,
			@Parameter(description = "Número de página", required = true) @RequestParam int page,
			@Parameter(description = "Tamaño de página", required = true) @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<CompraventaOutputDTO> resultado = servicio.recuperarCompraventas(idComprador, idVendedor, pageable);
		return pagedResourcesAssembler.toModel(resultado);
	}
	
	@Operation(summary = "Recuperar una compraventa por id", description = "Devuelve una compraventa a partir del id dado (usado para .NET)")
	@ApiResponse(responseCode = "200", description = "Compraventa encontrada.")
	@ApiResponse(responseCode = "404", description = "Compraventa no encontrada.")
	@GetMapping("/{id}")
	public ResponseEntity<CompraventaOutputDTO> recuperarCompraventa(@PathVariable String id){
		try {
			CompraventaOutputDTO compraventa = servicio.recuperarCompraventa(id);
			return ResponseEntity.ok(compraventa);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
