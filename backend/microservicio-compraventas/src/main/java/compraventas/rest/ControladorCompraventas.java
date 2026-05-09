package compraventas.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import compraventas.modelo.Compraventa;
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

	public ControladorCompraventas(ServicioCompraventas servicio) {
		this.servicio = servicio;
	}

	@Operation(summary = "Registrar una compraventa", description = "El comprador adquiere un producto")
	@ApiResponse(responseCode = "200", description = "Compraventa registrada. Devuelve el id de la compraventa.")
	@ApiResponse(responseCode = "403", description = "El usuario autenticado no coincide con el comprador.")
	@PostMapping
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<String> comprarProducto(
			@Parameter(description = "Identificador del producto a comprar", required = true) @RequestParam String idProducto,
			@Parameter(description = "Identificador del comprador", required = true) @RequestParam String idComprador,
			HttpServletRequest request) throws IOException {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idComprador)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return ResponseEntity.ok(servicio.registrarCompraventa(idProducto, idComprador));
	}

	@Operation(summary = "Listar compras de un usuario", description = "Devuelve las compraventas en las que el usuario es comprador")
	@ApiResponse(responseCode = "200", description = "Listado de compras devuelto correctamente.")
	@ApiResponse(responseCode = "403", description = "El usuario autenticado no coincide con el solicitado.")
	@GetMapping("/compras/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<List<Compraventa>> obtenerCompras(
			@Parameter(description = "Identificador del usuario comprador", required = true) @PathVariable String idUsuario,
			HttpServletRequest request) {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return ResponseEntity.ok(servicio.recuperarCompras(idUsuario));
	}

	@Operation(summary = "Listar ventas de un usuario", description = "Devuelve las compraventas en las que el usuario es vendedor")
	@ApiResponse(responseCode = "200", description = "Listado de ventas devuelto correctamente.")
	@ApiResponse(responseCode = "403", description = "El usuario autenticado no coincide con el solicitado.")
	@GetMapping("/ventas/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<List<Compraventa>> obtenerVentas(
			@Parameter(description = "Identificador del usuario vendedor", required = true) @PathVariable String idUsuario,
			HttpServletRequest request) {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return ResponseEntity.ok(servicio.recuperarVentas(idUsuario));
	}

	@Operation(summary = "Listar compraventas entre dos usuarios", description = "Solo accesible por administradores. Devuelve las compraventas entre un comprador y un vendedor concretos.")
	@ApiResponse(responseCode = "200", description = "Listado devuelto correctamente.")
	@GetMapping
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity<List<Compraventa>> obtenerCompraventas(
			@Parameter(description = "Identificador del comprador", required = true) @RequestParam String idComprador,
			@Parameter(description = "Identificador del vendedor", required = true) @RequestParam String idVendedor) {
		return ResponseEntity.ok(servicio.recuperarCompraventas(idComprador, idVendedor));
	}

}
