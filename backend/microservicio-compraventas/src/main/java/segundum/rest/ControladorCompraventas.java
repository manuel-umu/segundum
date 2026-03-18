package segundum.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import segundum.modelo.Compraventa;
import segundum.servicios.IServicioCompraventas;
import segundum.servicios.ServicioCompraventas;

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
	public String comprarProducto(@RequestParam String idProducto, @RequestParam String idComprador) {
		return servicio.registrarCompraventa(idProducto, idComprador);
	}

	// Recuperar lista de compraventas donde el comprador es el usuario id
	@GetMapping("/compras/{idUsuario}")
	public List<Compraventa> obtenerCompras(@PathVariable String idUsuario) {
		return servicio.recuperarCompras(idUsuario);
	}

	// Recuperar lista de compraventas donde el vendedor es el usuario id
	@GetMapping("/ventas/{idUsuario}")
	public List<Compraventa> obtenerVentas(@PathVariable String idUsuario) {
		return servicio.recuperarVentas(idUsuario);
	}
}
