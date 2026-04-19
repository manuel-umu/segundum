package umu.segundum.pasarela.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;
import umu.segundum.pasarela.dto.AuthResponseDTO;
import umu.segundum.pasarela.dto.LoginDTO;
import umu.segundum.pasarela.dto.UsuarioDTO;
import umu.segundum.pasarela.utils.JwtUtils;
import umu.segundum.pasarela.utils.CookieHelper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsuariosRestClient usuariosRestClient;

	@Autowired
	private JwtUtils jwtUtils;

	@Value("${jwt.cookie-name}")
	private String cookieName;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO credenciales, HttpServletResponse response)
			throws IOException {
		Response<UsuarioDTO> retrofitResponse = usuariosRestClient.verifyCredentials(credenciales).execute();
		if (!retrofitResponse.isSuccessful() || retrofitResponse.body() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
		return ResponseEntity.ok(payload);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		CookieHelper.clearJwtCookie(response, cookieName);
		return ResponseEntity.noContent().build();
	}
}