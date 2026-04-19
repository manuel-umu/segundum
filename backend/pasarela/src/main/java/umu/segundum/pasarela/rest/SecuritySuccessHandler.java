package umu.segundum.pasarela.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import umu.segundum.pasarela.dto.AuthResponseDTO;
import umu.segundum.pasarela.dto.UsuarioDTO;
import umu.segundum.pasarela.utils.JwtUtils;
import umu.segundum.pasarela.rest.UsuariosRestClient;
import umu.segundum.pasarela.utils.CookieHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UsuariosRestClient usuariosRestClient;

	@Autowired
	private JwtUtils jwtUtils;

	@Value("${jwt.cookie-name}")
	private String cookieName;

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
		
		// Mas rapido que el claims
		Object githubIdAttr = principal.getAttribute("id");
		
		if (githubIdAttr == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "GitHub no devolvio id");
			return;
		}
		String githubId = String.valueOf(githubIdAttr);

		Response<UsuarioDTO> retrofitResponse = usuariosRestClient.getByGithubId(githubId).execute();
		if (!retrofitResponse.isSuccessful() || retrofitResponse.body() == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario GitHub no registrado");
			return;
		}

		UsuarioDTO usuario = retrofitResponse.body();
		String roles = usuario.isAdmin() ? "USUARIO,ADMINISTRADOR" : "USUARIO";

		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", usuario.getId());
		claims.put("roles", roles);
		claims.put("nombre", usuario.getNombre());

		String token = jwtUtils.generateToken(claims);

		CookieHelper.writeJwtCookie(response, cookieName, token, (int) jwtUtils.getExpirationSeconds());

		AuthResponseDTO payload = new AuthResponseDTO(token, usuario.getId(),
				(usuario.getNombre() == null ? "" : usuario.getNombre())
						+ (usuario.getApellidos() == null ? "" : " " + usuario.getApellidos()),
				roles);

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(mapper.writeValueAsString(payload));
		response.getWriter().flush();
	}
}