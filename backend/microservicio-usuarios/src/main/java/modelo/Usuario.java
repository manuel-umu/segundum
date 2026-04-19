package modelo;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorios.Identificable;

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

	public Usuario() {
	}

	public Usuario(String nombre, String apellidos, String email, LocalDate fechaNac, String clave, String telefono) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNac = fechaNac;
		this.telefono = telefono;
		this.isAdmin = false;
		this.contCompras = 0;
		this.contVentas = 0;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", clave="
				+ clave + ", fechaNac=" + fechaNac + ", telefono=" + telefono + ", isAdmin=" + isAdmin
				+ ", contCompras=" + contCompras + ", contVentas=" + contVentas + ", githubId=" + githubId + "]";
	}

}
