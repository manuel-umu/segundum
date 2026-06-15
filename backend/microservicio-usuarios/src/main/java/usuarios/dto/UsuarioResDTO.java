package usuarios.dto;

public class UsuarioResDTO {

	private String email;
	private String nombre;
	private String apellidos;
	private String uri;
	private Integer contCompras;
	private Integer contVentas;

	public UsuarioResDTO() {
	}
	
	public UsuarioResDTO(String email, String nombre, String apellidos, String uri, Integer contCompras, Integer contVentas) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.uri = uri;
		this.contCompras = contCompras;
		this.contVentas = contVentas;
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

	public Integer getContCompras() {
		return contCompras;
	}

	public void setContCompras(Integer contCompras) {
		this.contCompras = contCompras;
	}

	public Integer getContVentas() {
		return contVentas;
	}

	public void setContVentas(Integer contVentas) {
		this.contVentas = contVentas;
	}
	
}
