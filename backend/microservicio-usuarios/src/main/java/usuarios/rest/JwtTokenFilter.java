package usuarios.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
/*
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

	private static final String COOKIE_NAME = "jwt";

	@Context
	private ResourceInfo resourceInfo;

	@Context
	private HttpServletRequest servletRequest;

	@Override
	public void filter(ContainerRequestContext requestContext) {

		if (resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class)) {
			return;
		}

		String token = extractToken(requestContext);

		if (token == null) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity("No se adjunta el token correctamente").build());
			return;
		}

		try {
			Claims claims = JwtUtils.validateToken(token);

			this.servletRequest.setAttribute("claims", claims);

			Set<String> roles = new HashSet<>(Arrays.asList(claims.get("roles", String.class).split(",")));

			if (this.resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class)) {

				String[] allowedRoles = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class).value();

				if (roles.stream().noneMatch(userRole -> Arrays.asList(allowedRoles).contains(userRole))) {
					requestContext.abortWith(
							Response.status(Response.Status.FORBIDDEN).entity("no tiene rol de acceso").build());
				}
			}
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build());
		}
	}

	private String extractToken(ContainerRequestContext requestContext) {
		String authorization = requestContext.getHeaderString("Authorization");
		if (authorization != null && authorization.startsWith("Bearer ")) {
			return authorization.substring("Bearer ".length()).trim();
		}
		if (servletRequest != null && servletRequest.getCookies() != null) {
			for (Cookie cookie : servletRequest.getCookies()) {
				if (COOKIE_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}*/
