package segundum.rest;

import org.springframework.data.domain.Pageable;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import segundum.dto.LugarRecogidaDTO;
import segundum.dto.ProductoDTO;
import segundum.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;

import segundum.modelo.Producto;
import segundum.modelo.Usuario;
import segundum.servicios.IServicioProductos;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ControladorProductos /* implements ProductosApi */ {

	@Autowired
	public IServicioProductos servicio;

	/*
	 * @Autowired private PagedResourcesAssembler<Producto> pagedResourcesAssembler;
	 */
	@Autowired
	public ControladorProductos(IServicioProductos servicio) {
		this.servicio = servicio;
	}

	// Dar de alta un producto
	@PostMapping
	public ResponseEntity<Void> createProducto(@Valid @RequestBody ProductoDTO p) throws Exception {
		String id = servicio.crear(p.getTitulo(), p.getDescripcion(), p.getPrecio(), p.getEstado(),
				p.getCategoria().getId(), p.getEnvioDispo(), p.getVendedor().getId());
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();
	}

	// Recuperar un usuario
	@GetMapping("/{id}")
	public EntityModel<ProductoDTO> getProducto(@PathVariable String id) throws Exception {
		Producto producto = servicio.recuperar(id);
		ProductoDTO dto = productoToProductoDto(producto);
		EntityModel<ProductoDTO> model = EntityModel.of(dto);
		// Para que tenga referencia a si mismo
		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControladorProductos.class).getProducto(id))
				.withSelfRel());
		return model;
	}

	// Listado de productos
	@GetMapping
	public Page<ProductoDTO> getProductos(Pageable paginacion) throws Exception {
		Page<ProductoDTO> resultado = servicio.getListadoPaginado(paginacion);
		return resultado;
	}
	/*
	 * @GetMapping public PagedModel<EntityModel<ProductoDTO>> getProductos(Pageable
	 * paginacion) throws Exception { Page<ProductoDTO> resultado =
	 * servicio.getListadoPaginado(paginacion); //TODO: ¿Habria que utilizar
	 * productoRes? return this.pagedResourcesAssembler.toModel(resultado,
	 * encuestaResumenAssembler); }
	 */

	// Asignar lugar de recogida a un producto
	/*
	 * @PutMapping("/{id}/recogida") public ResponseEntity<Void>
	 * asignRecogida(@PathVariable String id, @RequestBody LugarRecogidaDTO
	 * recogida) throws Exception { servicio.asignarRecogida(id,
	 * recogida.getLongitud(), recogida.getLatitud(), recogida.getDescripcion());
	 * return Response.status(Response.Status.NO_CONTENT).build(); }
	 */

	private UsuarioDTO toUsuarioDTO(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail());
	}

	private ProductoDTO productoToProductoDto(Producto producto) {
		UsuarioDTO usuario = toUsuarioDTO(producto.getVendedor());
		return new ProductoDTO(producto.getTitulo(), producto.getDescripcion(), producto.getPrecio(),
				producto.getEstado(), producto.getFechaPubli(), producto.getCategoria(), producto.getVisualizaciones(),
				producto.isEnvioDispo(), producto.getRecogida(), usuario);
	}

}
