package rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dto.LugarRecogidaDTO;
import dto.ProductoDTO;
import dto.UsuarioDTO;
import modelo.LugarRecogida;
import modelo.Producto;
import modelo.Usuario;
import servicios.FactoriaServicios;
import servicios.IServicioProductos;

@Path("productos")

public class ControladorProductos {
	public IServicioProductos servicio = FactoriaServicios.getServicio(IServicioProductos.class);
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
			//URI uri = uriInfo.getAbsolutePathBuilder().path(p.getId()).build();
			dtos.add(productoToProductoDto(p));
		}
		return Response.status(Response.Status.OK).entity(dtos).build();
	}

	private UsuarioDTO toUsuarioDTO(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail());
	}

	private ProductoDTO productoToProductoDto(Producto producto) {
		UsuarioDTO usuario = toUsuarioDTO(producto.getVendedor());
		return new ProductoDTO(producto.getTitulo(), producto.getDescripcion(), producto.getPrecio(), producto.getEstado(), producto.getFechaPubli(), producto.getCategoria(), producto.getVisualizaciones(), producto.isEnvioDispo(), producto.getRecogida(), usuario);
	}

}
