package productos.dto;

import productos.modelo.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad Usuario")
public class UsuarioDTO {
	@Schema(description = "Identificador único del usuario")
	private String id;
	@Schema(description = "Correo electrónico del usuario", example = "usuario@ejemplo.com")
	private String email;
	@Schema(description = "Nombre del usuario", example = "Juan")
	private String nombre;
	@Schema(description = "Apellidos del usuario", example = "Garcia López")
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