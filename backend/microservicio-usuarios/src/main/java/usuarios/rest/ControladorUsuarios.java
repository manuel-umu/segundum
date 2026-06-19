package usuarios.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import usuarios.dto.LoginDTO;
import usuarios.dto.UsuarioAuthDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.dto.UsuarioResDTO;
import io.jsonwebtoken.Claims;
import usuarios.modelo.Usuario;
import usuarios.repositorios.RepositorioException;
import usuarios.servicios.FactoriaServicios;
import usuarios.servicios.IServicioUsuarios;

@Path("usuarios")
public class ControladorUsuarios {
	public IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
	@Context
	private UriInfo uriInfo;
	@Context
	private HttpServletRequest servletRequest;

	@POST
	@Path("/verify-credentials")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response verifyCredentials(LoginDTO credenciales) {
		try {
			Usuario usuario = servicio.login(credenciales.getUsername(), credenciales.getPassword());
			UsuarioAuthDTO dto = new UsuarioAuthDTO(usuario.getId(), usuario.getEmail(), usuario.getNombre(),
					usuario.getApellidos(), usuario.getGithubId(), usuario.isAdmin());
			return Response.ok(dto).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@GET
	@Path("/github/{githubId}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getByGithub(@PathParam("githubId") String githubId) {
		try {
			Usuario usuario = servicio.findByGithubId(githubId);
			if (usuario == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			UsuarioAuthDTO dto = new UsuarioAuthDTO(usuario.getId(), usuario.getEmail(), usuario.getNombre(),
					usuario.getApellidos(), usuario.getGithubId(), usuario.isAdmin());
			return Response.ok(dto).build();
		} catch (RepositorioException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed("USUARIO")
	public Response getUsuario(@PathParam("id") String id) throws Exception {
		Usuario usuario = servicio.recuperar(id);
		UsuarioDTO dto = Usuario.toDto(usuario);
		return Response.status(Response.Status.OK).entity(dto).build();
	}

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

	@DELETE
	@Path("/{id}")
	@RolesAllowed("USUARIO")
	public Response removeActividad(@PathParam("id") String id) throws Exception {
		servicio.borrar(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response createUsuario(UsuarioDTO u) throws Exception {
		System.err.println(u.toString());
		LocalDate fecha = u.getFechaNac() != null ? LocalDate.parse(u.getFechaNac()) : null;
		String id = servicio.crear(u.getNombre(), u.getApellidos(), u.getEmail(), u.getFechaNac(), u.getClave(),
				u.getTelefono(), u.isAdmin());
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}

	private UsuarioResDTO toResDto(UsuarioDTO usuario, URI uri) {
		return new UsuarioResDTO(usuario.getEmail(), usuario.getNombre(), usuario.getApellidos(), uri.toString(), usuario.getContCompras(), usuario.getContVentas());
	}

	@GET
	@Path("/{id}/nombre")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getNombreUsuario(@PathParam("id") String id) throws Exception {
		Usuario usuario = servicio.recuperar(id);
		Map<String, String> resultado = new HashMap<>();
		resultado.put("id", usuario.getId());
		resultado.put("nombre", usuario.getNombre() + " " + usuario.getApellidos());
		return Response.ok(resultado).build();
	}

}
