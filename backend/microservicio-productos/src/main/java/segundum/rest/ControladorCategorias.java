package segundum.rest;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import segundum.modelo.Categoria;
import segundum.servicios.IServicioCategorias;

@RestController
@RequestMapping("/categorias")
public class ControladorCategorias {
	
	@Autowired
	public IServicioCategorias servicio;

	@Autowired
	public ControladorCategorias(IServicioCategorias servicio) {
		this.servicio = servicio;
	}
	
	// Recuperar categorías raíz
	@GetMapping
	public List<EntityModel<Categoria>> getCategoriaRaiz() throws Exception {
		LinkedList<Categoria> categorias = servicio.recuperarCategoriaRaiz();
		List<EntityModel<Categoria>> resultado = new LinkedList<>();
		for(Categoria c : categorias) {
			EntityModel<Categoria> model = EntityModel.of(c);
			model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControladorCategorias.class).getDescendientes(c.getId()))
					.withSelfRel());
			resultado.add(model);
		}
		return resultado;
	}
	
	// Recuperar descendientes de una categoría
	@GetMapping("/{id}")
	public List<EntityModel<Categoria>> getDescendientes(@PathVariable String id) throws Exception {
		LinkedList<Categoria> categorias = servicio.recuperarDescCategoria(id);
		List<EntityModel<Categoria>> resultado = new LinkedList<>();
		for(Categoria c : categorias) {
			EntityModel<Categoria> model = EntityModel.of(c);
			model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControladorCategorias.class).getDescendientes(c.getId()))
					.withSelfRel());
			resultado.add(model);
		}
		return resultado;
	}
}
