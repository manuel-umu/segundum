package segundum.dto;


public class UsuarioDTO {
	private String id;
	private String email;
	private String nombre;
	private String apellidos;

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

}