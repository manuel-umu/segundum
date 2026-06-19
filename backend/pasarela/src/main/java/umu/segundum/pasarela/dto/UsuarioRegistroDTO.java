package umu.segundum.pasarela.dto;

import java.time.LocalDate;

public class UsuarioRegistroDTO {
	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
	private String telefono;
	private String fechaNac;
	private boolean isAdmin;

	public UsuarioRegistroDTO() {
	}

	public UsuarioRegistroDTO(String nombre, String apellidos, String email, String clave, String fechaNac,
			String telefono, boolean isAdmin) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNac = fechaNac;
		this.telefono = telefono;
		this.isAdmin = isAdmin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
}
