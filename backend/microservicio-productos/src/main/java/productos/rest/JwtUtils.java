package productos.rest;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration-seconds}")
	private long expirationSeconds;

	public String generateToken(Map<String, Object> claims) {
		Date caducidad = Date.from(Instant.now().plusSeconds(expirationSeconds));
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret)
				.setExpiration(caducidad).compact();
	}

	public Claims validateToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}
