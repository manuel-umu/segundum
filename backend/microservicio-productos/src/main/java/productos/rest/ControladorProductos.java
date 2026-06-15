package productos.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.net.URI;
import java.util.List;
import productos.dto.LugarRecogidaDTO;
import productos.dto.ProductoDTO;
import productos.dto.ProductoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import productos.modelo.Producto;
import productos.modelo.ProductoRes;
import productos.servicios.IServicioProductos;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ControladorProductos implements ProductosApi {

	@Autowired
	public IServicioProductos servicio;

	@Autowired
	private PagedResourcesAssembler<ProductoResDTO> pagedResourcesAssembler;

	@Autowired
	public ControladorProductos(IServicioProductos servicio) {
		this.servicio = servicio;
	}

	// Dar de alta un producto
	@PostMapping
	@PreAuthorize("hasAuthority('USUARIO')")
	@Override
	public ResponseEntity<Void> createProducto(@Valid @RequestBody ProductoDTO p, HttpServletRequest request)
			throws Exception {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(p.getVendedor().getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		String id = servicio.crear(p.getTitulo(), p.getDescripcion(), p.getPrecio(), p.getEstado(),
				p.getCategoria().getId(), p.getEnvioDispo(), p.getVendedor().getId());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	// Recuperar un producto
	@GetMapping("/{id}")
	@Override
	public EntityModel<ProductoResDTO> getProducto(@PathVariable String id) throws Exception {
		Producto producto = servicio.recuperar(id);
		ProductoResDTO dto = ProductoResDTO.toDto(producto);
		EntityModel<ProductoResDTO> model = EntityModel.of(dto);
		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControladorProductos.class).getProducto(id))
				.withSelfRel());
		return model;
	}

	// Listado (paginado) de productos
	@GetMapping
	@Override
	public PagedModel<EntityModel<ProductoResDTO>> getProductos(@RequestParam int page, @RequestParam int size)
			throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("titulo").ascending());
		Page<ProductoResDTO> resultado = servicio.getListadoPaginado(paginacion);
		return this.pagedResourcesAssembler.toModel(resultado);
	}

	// Asignar lugar de recogida a un producto
	@PutMapping("/{id}/recogida")
	@PreAuthorize("hasAuthority('USUARIO')")
	@Override
	public ResponseEntity<Void> asignRecogida(@PathVariable String id, @RequestBody LugarRecogidaDTO recogida,
			HttpServletRequest request) throws Exception {
		Producto p = servicio.recuperar(id);
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(p.getVendedor().getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		servicio.asignarRecogida(id, recogida.getLongitud(), recogida.getLatitud(), recogida.getDescripcion());
		return ResponseEntity.noContent().build();
	}

	// Añadir visualización a un producto
	@PatchMapping("/{id}/visualizaciones")
	@Override
	public ResponseEntity<Void> addVisualizacion(@PathVariable String id) throws Exception {
		servicio.añadirVisualizacion(id);
		return ResponseEntity.noContent().build();
	}

	// Recuperar productos de un mes determinado
	@GetMapping("/historial/year/{year}/mes/{mes}")
	@Override
	public ResponseEntity<List<ProductoRes>> getHistorial(@PathVariable Integer year, @PathVariable Integer mes)
			throws Exception {
		return ResponseEntity.ok(servicio.historialMes(mes, year));
	}

	// Recuperar los productos (paginados) de un usuario (vendedor) dado
	@GetMapping("/usuario/{idUsuario}")
	@PreAuthorize("hasAuthority('USUARIO')")
	@Override
	public ResponseEntity<PagedModel<EntityModel<ProductoResDTO>>> getProductosUsuario(@PathVariable String idUsuario,
			@RequestParam int page, @RequestParam int size, HttpServletRequest request) throws Exception {
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(idUsuario)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		Pageable paginacion = PageRequest.of(page, size, Sort.by("titulo").ascending());
		Page<ProductoResDTO> resultado = servicio.productosDeUsuario(idUsuario, paginacion);
		return ResponseEntity.ok(this.pagedResourcesAssembler.toModel(resultado));
	}

	// Modifica ciertas propiedades de un producto
	@PatchMapping("/{id}")
	@PreAuthorize("hasAuthority('USUARIO')")
	@Override
	public ResponseEntity<Void> modificarProducto(@PathVariable String id, @RequestBody ProductoDTO p,
			HttpServletRequest request) throws Exception {
		Producto producto = servicio.recuperar(id);
		Claims claims = (Claims) request.getAttribute("claims");
		if (!claims.getSubject().equals(producto.getVendedor().getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		servicio.actualizar(id, p.getPrecio(), p.getDescripcion());
		return ResponseEntity.noContent().build();
	}
}
