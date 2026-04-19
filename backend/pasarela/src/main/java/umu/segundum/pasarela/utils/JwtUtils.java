package umu.segundum.pasarela.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration-seconds}")
	private long expirationSeconds;

	public String generateToken(Map<String, Object> claims) {
		Date expiracion = Date.from(Instant.now().plusSeconds(expirationSeconds));
		return Jwts.builder().setClaims(claims).setExpiration(expiracion).signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public Claims validateToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public long getExpirationSeconds() {
		return expirationSeconds;
	}
}