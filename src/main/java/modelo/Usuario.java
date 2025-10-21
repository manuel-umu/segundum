package modelo;

import java.time.LocalDate;
import java.util.Random;

import javax.persistence.metamodel.IdentifiableType;

import repositorios.Identificable;

public class Usuario implements Identificable {

	private String id;
	private String email;
	private String nombre;
	private String apellidos;
	private String clave;
	private LocalDate fechaNac;
	private String telefono;
	private boolean isAdmin;

	public Usuario(String nombre, String apellidos, String email, LocalDate fechaNac, String clave, String telefono) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNac = fechaNac;
		this.telefono = telefono;
		this.isAdmin = false;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
