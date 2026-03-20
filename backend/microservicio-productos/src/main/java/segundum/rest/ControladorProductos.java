package segundum.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URI;

import segundum.dto.LugarRecogidaDTO;
import segundum.dto.ProductoDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import segundum.modelo.Producto;
import segundum.servicios.IServicioProductos;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ControladorProductos /* implements ProductosApi */ {

	@Autowired
	public IServicioProductos servicio;

	@Autowired
	private PagedResourcesAssembler<ProductoDTO> pagedResourcesAssembler;

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
		ProductoDTO dto = ProductoDTO.toDto(producto);
		EntityModel<ProductoDTO> model = EntityModel.of(dto);
		// Para que tenga referencia a si mismo
		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControladorProductos.class).getProducto(id))
				.withSelfRel());
		return model;
	}

	// Listado (paginado) de productos
	@GetMapping
	public PagedModel<EntityModel<ProductoDTO>> getProductos(@RequestParam int page, @RequestParam int size)
			throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("titulo").ascending());
		Page<ProductoDTO> resultado = servicio.getListadoPaginado(paginacion);
		return this.pagedResourcesAssembler.toModel(resultado);
	}

	// Asignar lugar de recogida a un producto
	@PutMapping("/{id}/recogida")
	public ResponseEntity<Void> asignRecogida(@PathVariable String id, @RequestBody LugarRecogidaDTO recogida)
			throws Exception {
		servicio.asignarRecogida(id, recogida.getLongitud(), recogida.getLatitud(), recogida.getDescripcion());
		return ResponseEntity.noContent().build();
	}

}
