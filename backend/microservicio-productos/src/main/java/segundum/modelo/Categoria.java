package segundum.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.*;

import segundum.repositorios.Identificable;

@XmlRootElement(name = "categoria")
@Entity
@Table(name = "categoria")
public class Categoria implements Identificable {
	@Id
	private String id;
	private String nombre;
	private String descripcion;
	private String ruta;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoria_fk")
	private List<Categoria> subcategorias = new ArrayList<>();

	public Categoria() {
	}

	public Categoria(String id, String nombre, String descripcion, String ruta) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ruta = ruta;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlTransient
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@XmlAttribute
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@XmlElement(name = "categoria")
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ruta=" + ruta
				+ ", subcategoria=" + subcategorias + "]";
	}

}
