package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dto.UsuarioDTO;
import dto.UsuarioResDTO;
import io.jsonwebtoken.Claims;
import modelo.Usuario;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

@Path("usuarios")
public class ControladorUsuarios {
	public IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
	@Context
	private UriInfo uriInfo;
	@Context
	private HttpServletRequest servletRequest;

	// Recuperar un usuario
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed("USUARIO")
	public Response getUsuario(@PathParam("id") String id) throws Exception {
		Usuario usuario = servicio.recuperar(id);
		UsuarioDTO dto = toDto(usuario);
		return Response.status(Response.Status.OK).entity(dto).build();
	}

	// Listado de usuarios
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed("USUARIO")
	public Response getUsuarios() throws Exception {
		List<UsuarioDTO> dtos = servicio.listar();
		List<UsuarioResDTO> resDtos = new LinkedList<UsuarioResDTO>();
		for (UsuarioDTO dto : dtos) {
			URI uri = uriInfo.getAbsolutePathBuilder().path(dto.getId()).build();
			resDtos.add(toResDto(dto, uri));
		}
		return Response.status(Response.Status.OK).entity(resDtos).build();
	}

	// Modificar un usuario
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("USUARIO")
	public Response update(@PathParam("id") String id, UsuarioDTO usuario) throws Exception {

		Claims claims = (Claims) this.servletRequest.getAttribute("claims");
		String autenticado = claims.getSubject();

		if (!autenticado.equals(id)) {
			return Response.status(Response.Status.FORBIDDEN).entity("No tienes permiso para modificar este usuario")
					.build();
		}

		servicio.actualizar(id, usuario.getNombre(), usuario.getApellidos(), usuario.getClave(), usuario.getFechaNac(),
				usuario.getTelefono());
		return Response.status(Response.Status.NO_CONTENT).build();

	}

	// Borrar actividad
	@DELETE
	@Path("/{id}")
	@RolesAllowed("USUARIO")
	public Response removeActividad(@PathParam("id") String id) throws Exception {
		servicio.borrar(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// Dar de alta un usuario
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response createUsuario(UsuarioDTO u) throws Exception {
		String id = servicio.crear(u.getNombre(), u.getApellidos(), u.getEmail(), u.getFechaNac(), u.getClave(),
				u.getTelefono());
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}

	private UsuarioDTO toDto(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(),
				usuario.getFechaNac(), usuario.getClave(), usuario.getTelefono());
	}

	private UsuarioResDTO toResDto(UsuarioDTO usuario, URI uri) {
		return new UsuarioResDTO(usuario.getEmail(), usuario.getNombre(), usuario.getApellidos(), uri.toString());
	}

}
