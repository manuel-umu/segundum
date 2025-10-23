package modelo;

import repositorios.Identificable;

public class Categoria implements Identificable {
	private String id;
	private String nombre;
	private String descripcion;
	private String ruta;
	private Categoria subcategoria;

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

}
