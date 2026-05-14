package usuarios.dto;

import java.time.LocalDate;

public class UsuarioDTO {
	private String id;
	private String email;
	private String nombre;
	private String apellidos;
	private String clave;
	private LocalDate fechaNac;
	private String telefono;
	private boolean isAdmin;
	private String githubId;
	private Integer numValoracionesComprador;
	private Integer numValoracionesVendedor;
	private Double mediaComprador;
	private Double mediaVendedor;

	public UsuarioDTO() {
	}
	
	public UsuarioDTO(String id, String email, String nombre, String apellidos, String clave, LocalDate fechaNac,
			String telefono) {
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNac = fechaNac;
		this.telefono = telefono;
		this.isAdmin = false;
		this.numValoracionesComprador = 0;
		this.numValoracionesVendedor = 0;
		this.mediaComprador = 0.0;
		this.mediaVendedor = 0.0;
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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getGithubId() {
		return githubId;
	}

	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}
	
	public Integer getNumValoracionesComprador() {
		return numValoracionesComprador;
	}

	public void setNumValoracionesComprador(Integer numValoracionesComprador) {
		this.numValoracionesComprador = numValoracionesComprador;
	}

	public Integer getNumValoracionesVendedor() {
		return numValoracionesVendedor;
	}

	public void setNumValoracionesVendedor(Integer numValoracionesVendedor) {
		this.numValoracionesVendedor = numValoracionesVendedor;
	}

	public Double getMediaComprador() {
		return mediaComprador;
	}

	public void setMediaComprador(Double mediaComprador) {
		this.mediaComprador = mediaComprador;
	}

	public Double getMediaVendedor() {
		return mediaVendedor;
	}

	public void setMediaVendedor(Double mediaVendedor) {
		this.mediaVendedor = mediaVendedor;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", clave=" + clave + ", fechaNac=" + fechaNac + ", telefono=" + telefono + ", isAdmin=" + isAdmin
				+ ", githubId=" + githubId + ", numValoracionesComprador=" + numValoracionesComprador
				+ ", numValoracionesVendedor=" + numValoracionesVendedor + ", mediaComprador=" + mediaComprador
				+ ", mediaVendedor=" + mediaVendedor + "]";
	}

}
