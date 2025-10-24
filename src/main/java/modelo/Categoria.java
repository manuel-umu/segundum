package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import repositorios.Identificable;

@XmlRootElement
//@Entity
public class Categoria implements Identificable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	private String nombre;
	private String descripcion;
	private String ruta;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoria_fk")
	private Categoria subcategoria;

	public Categoria() {
	}

	public Categoria(String id, String nombre, String descripcion, String ruta, Categoria subcategoria) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ruta = ruta;
		this.subcategoria = subcategoria;
	}

	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public Categoria getSubcategoria() {
		return subcategoria;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ruta=" + ruta
				+ ", subcategoria=" + subcategoria + "]";
	}

}
