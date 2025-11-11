package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;

import repositorios.Identificable;

@XmlRootElement(name="categoria")
@Entity
@Table(name = "categoria")
public class Categoria implements Identificable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
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

	@XmlElement(name="nombre")
	public String getNombre() {
		return nombre;
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
	
	@XmlElement(name="categoria")
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", ruta=" + ruta
				+ ", subcategoria=" + subcategorias + "]";
	}

}
