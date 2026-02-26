package rest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class ControladorAuth {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		/*Map<String, Object> claims = verificarCredenciales(username, password);
		if (claims != null) {
		String token = // generar el token
		return Response.ok(token).build();
		} else {
		return Response
		.status(Response.Status.UNAUTHORIZED)
		.entity("Credenciales inválidas").build();
		}*/
		return null;
	}
	
	
}
