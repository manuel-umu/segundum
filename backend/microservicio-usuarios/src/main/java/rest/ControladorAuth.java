package rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.LoginDTO;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

@Path("auth")
public class ControladorAuth {
	
	private IServicioUsuarios servicioU = FactoriaServicios.getServicio(IServicioUsuarios.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	@PermitAll
	public Response login(LoginDTO credenciales) throws RepositorioException, EntidadNoEncontrada {
		
		Map<String, Object> claims = verificarCredenciales(credenciales.getUsername(), credenciales.getPassword());
		if (claims != null) {
			String token = JwtUtils.generateToken(claims);
			return Response.ok(token).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
		}
		
	}
	
	private Map<String, Object> verificarCredenciales(String username, String password) throws RepositorioException, EntidadNoEncontrada {
		
		Usuario usuario = servicioU.login(username, password);
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", usuario.getId());
		claims.put("email", usuario.getEmail());
		claims.put("roles", "USUARIO");
		
		return claims;
	}
	
}
