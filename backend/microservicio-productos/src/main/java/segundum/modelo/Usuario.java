package segundum.modelo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import segundum.repositorios.Identificable;

@Entity
@Table(name = "usuario")
public class Usuario implements Identificable {
	@Id
	private String id;
	private String email;
	private String nombre;
	private String apellidos;

	public Usuario() {
	}

	public Usuario(String nombre, String apellidos, String email) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}
}
