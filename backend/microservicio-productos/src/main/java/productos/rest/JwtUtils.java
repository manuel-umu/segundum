package productos.rest;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

	@Value("${jwt.secret}")
	private static String SECRETO;
	@Value("${jwt.expiration-seconds}")
	private static final long TIEMPO = 3600;

	public static String generateToken(Map<String, Object> claims) {

		Date caducidad = Date.from(Instant.now().plusSeconds(TIEMPO));

		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRETO)
				.setExpiration(caducidad).compact();

		return token;
	}

	public static Claims validateToken(String token) {

		Claims claims = Jwts.parser().setSigningKey(SECRETO).parseClaimsJws(token).getBody();

		return claims;
	}
}
