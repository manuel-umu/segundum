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
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/compraventas")
public class ControladorCompraventas {
	@Autowired
	private IServicioCompraventas servicio;

	public ControladorCompraventas(ServicioCompraventas servicio) {
		this.servicio = servicio;
	}

	// Comprar un producto
	@PostMapping
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<String> comprarProducto(@RequestParam String idProducto, @RequestParam String idComprador, HttpServletRequest request) throws IOException {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idComprador)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return ResponseEntity.ok(servicio.registrarCompraventa(idProducto, idComprador));
	}

	// Recuperar lista de compraventas donde el comprador es el usuario id
	@GetMapping("/compras/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<List<Compraventa>> obtenerCompras(@PathVariable String idUsuario, HttpServletRequest request) {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return ResponseEntity.ok(servicio.recuperarCompras(idUsuario));
	}

	// Recuperar lista de compraventas donde el vendedor es el usuario id
	@GetMapping("/ventas/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	public ResponseEntity<List<Compraventa>> obtenerVentas(@PathVariable String idUsuario, HttpServletRequest request) {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return ResponseEntity.ok(servicio.recuperarVentas(idUsuario));
	}
	
	// Recuperar lista de compraventas entre un comprador y un vendedor
	@GetMapping
	@PreAuthorize("hasAuthority('ADMINISTRADOR')")
	public ResponseEntity<List<Compraventa>> obtenerCompraventas(@RequestParam String idComprador, @RequestParam String idVendedor) {
		return ResponseEntity.ok(servicio.recuperarCompraventas(idComprador, idVendedor));
	}
	
}
