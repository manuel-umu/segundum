package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dto.UsuarioDTO;
import modelo.Usuario;
import repositorios.FactoriaRepositorios;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

@Path("usuarios")
public class ControladorUsuarios {
	public IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuario(@PathParam("id") String id) throws Exception {
		Usuario usuario = servicio.recuperar(id);
		UsuarioDTO dto = toDto(usuario);
		return Response.status(Response.Status.OK).entity(dto).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuarios() throws Exception {
		List<UsuarioDTO> dtos = servicio.listar();
		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (UsuarioDTO dto : dtos) {
			usuarios.add(fromDto(dto));
		}
		return Response.status(Response.Status.OK).entity(dtos).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id, UsuarioDTO usuario) throws Exception {
		servicio.actualizar(id, usuario.getNombre(), usuario.getApellidos(), usuario.getClave(), usuario.getFechaNac(),
				usuario.getTelefono());
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Path("/{id}")
	public Response removeActividad(@PathParam("id") String id) throws Exception {
		servicio.borrar(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createUsuario(UsuarioDTO u) throws Exception {
		String id = servicio.crear(u.getNombre(), u.getApellidos(), u.getEmail(), u.getFechaNac(), u.getClave(),
				u.getTelefono());
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}

	private UsuarioDTO toDto(Usuario usuario) {
		return new UsuarioDTO(usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getFechaNac(),
				usuario.getClave(), usuario.getTelefono());
	}

	private Usuario fromDto(UsuarioDTO usuario) {
		return new Usuario(usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getFechaNac(),
				usuario.getClave(), usuario.getTelefono());
	}

}
