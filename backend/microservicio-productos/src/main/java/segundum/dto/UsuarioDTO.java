package segundum.dto;

import segundum.modelo.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad Usuario")
public class UsuarioDTO {
	@Schema(description = "Identificador del usuario")
	private String id;
	private String email;
	private String nombre;
	private String apellidos;

	public UsuarioDTO() {
	}

	public UsuarioDTO(String id, String email, String nombre, String apellidos) {
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;

	}

	public String getId() {
		return id;
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
		return "UsuarioDTO [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}

	public static UsuarioDTO toDto(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail());
	}

}