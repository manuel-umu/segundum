package usuarios.eventos;

public class EventoUsuarioCreado extends Evento{
	private String nombre;
	private String apellidos;
	private String email;
	
	public EventoUsuarioCreado(String id, String nombre, String apellidos, String email) {
		super(id, "usuario-creado");
		this.nombre = nombre;
		this.apellidos = apellidos;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
