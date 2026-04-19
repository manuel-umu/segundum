package umu.segundum.pasarela.dto;

public class AuthResponseDTO {
	private String token;
	private String id;
	private String nombre;
	private String roles;

	public AuthResponseDTO() {
	}

	public AuthResponseDTO(String token, String id, String nombre, String roles) {
		this.token = token;
		this.id = id;
		this.nombre = nombre;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}