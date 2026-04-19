package umu.segundum.pasarela.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

	public static void writeJwtCookie(HttpServletResponse response, String name, String jwt, int maxAgeSeconds) {
		Cookie cookie = new Cookie(name, jwt);
		cookie.setMaxAge(maxAgeSeconds);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void clearJwtCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}