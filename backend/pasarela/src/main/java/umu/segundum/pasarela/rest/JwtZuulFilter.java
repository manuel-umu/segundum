package umu.segundum.pasarela.rest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import umu.segundum.pasarela.utils.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtZuulFilter extends ZuulFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Value("${jwt.cookie-name}")
	private String cookieName;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String token = extractToken(request);
		if (token == null) {
			return null;
		}

		try {
			Claims claims = jwtUtils.validateToken(token);
			ctx.addZuulRequestHeader("Authorization", "Bearer " + token);
			ctx.addZuulRequestHeader("X-User-Id", claims.getSubject());
		} catch (Exception e) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			ctx.setResponseBody("Token JWT invalido o expirado");
		}
		return null;
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring("Bearer ".length()).trim();
		}
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}