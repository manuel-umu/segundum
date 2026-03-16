package segundum.rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import javax.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import segundum.dto.LugarRecogidaDTO;
import segundum.dto.ProductoDTO;
import segundum.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import segundum.modelo.LugarRecogida;
import segundum.modelo.Producto;
import segundum.modelo.Usuario;
import segundum.servicios.IServicioProductos;

@Component
@Path("productos")
public class ControladorProductos {
	@Autowired
	public IServicioProductos servicio;
	@Context
	private UriInfo uriInfo;

	// Dar de alta un producto
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProducto(ProductoDTO p) throws Exception {
		String id = servicio.crear(p.getTitulo(), p.getDescripcion(), p.getPrecio(), p.getEstado(),
				p.getCategoria().getId(), p.getEnvioDispo(), p.getVendedor().getId());
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}

	// Asignar lugar de recogida a un producto
	@PUT
	@Path("/{id}/recogida")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response asignRecogida(@PathParam("id") String id, LugarRecogidaDTO recogida) throws Exception {
		servicio.asignarRecogida(id, recogida.getLongitud(), recogida.getLatitud(), recogida.getDescripcion());
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// Recuperar un usuario
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProducto(@PathParam("id") String id) throws Exception {
		Producto producto = servicio.recuperar(id);
		ProductoDTO dto = productoToProductoDto(producto);
		return Response.status(Response.Status.OK).entity(dto).build();
	}

	// Listado de productos
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductos() throws Exception {
		List<Producto> productos = servicio.listar();
		List<ProductoDTO> dtos = new LinkedList<ProductoDTO>();
		for (Producto p : productos) {
			// URI uri = uriInfo.getAbsolutePathBuilder().path(p.getId()).build();
			dtos.add(productoToProductoDto(p));
		}
		return Response.status(Response.Status.OK).entity(dtos).build();
	}

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
