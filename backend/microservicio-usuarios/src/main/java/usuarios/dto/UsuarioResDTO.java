package usuarios.dto;

public class UsuarioResDTO {

	private String email;
	private String nombre;
	private String apellidos;
	private String uri;

	public UsuarioResDTO() {
	}
	
	public UsuarioResDTO(String email, String nombre, String apellidos, String uri) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.uri = uri;
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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
