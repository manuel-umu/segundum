package usuarios.modelo;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import usuarios.dto.UsuarioDTO;
import usuarios.repositorios.Identificable;

@Entity
@Table(name = "usuario")
public class Usuario implements Identificable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	private String email;
	private String nombre;
	private String apellidos;
	private String clave;
	private LocalDate fechaNac;
	private String telefono;
	private boolean isAdmin;
	private Integer contCompras;
	private Integer contVentas;
	private String githubId;
	private Integer numValoracionesComprador;
	private Integer numValoracionesVendedor;
	private Double mediaComprador;
	private Double mediaVendedor;

	public Usuario() {
	}

	public Usuario(String nombre, String apellidos, String email, LocalDate fechaNac, String clave, String telefono,
			boolean isAdmin) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNac = fechaNac;
		this.telefono = telefono;
		this.isAdmin = isAdmin;
		this.contCompras = 0;
		this.contVentas = 0;
		this.numValoracionesComprador = 0;
		this.numValoracionesVendedor = 0;
		this.mediaComprador = 0.0;
		this.mediaVendedor = 0.0;
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
		return "Usuario [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", clave="
				+ clave + ", fechaNac=" + fechaNac + ", telefono=" + telefono + ", isAdmin=" + isAdmin
				+ ", contCompras=" + contCompras + ", contVentas=" + contVentas + ", githubId=" + githubId + "]";
	}

	public static UsuarioDTO toDto(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNombre(), usuario.getApellidos(),
				usuario.getClave(), usuario.getFechaNac(), usuario.getTelefono(), usuario.isAdmin(),
				usuario.getContCompras(), usuario.getContVentas(), usuario.getNumValoracionesComprador(),
				usuario.getNumValoracionesVendedor(), usuario.getMediaComprador(), usuario.getMediaVendedor());
	}
}
