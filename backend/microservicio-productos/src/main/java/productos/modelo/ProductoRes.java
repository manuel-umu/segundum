package productos.modelo;

import java.time.LocalDateTime;

public class ProductoRes {

	private String id;
	private String titulo;
	private Float precio;
	private LocalDateTime fechaPubli;
	private String categoria;
	private Integer visualizaciones;
	
	public ProductoRes(String id, String titulo, Float precio, LocalDateTime fechaPubli, String categoria, Integer visualizaciones) {
		this.id = id;
		this.titulo = titulo;
		this.precio = precio;
		this.fechaPubli = fechaPubli;
		this.categoria = categoria;
		this.visualizaciones = visualizaciones;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public LocalDateTime getFechaPubli() {
		return fechaPubli;
	}

	public void setFechaPubli(LocalDateTime fechaPubli) {
		this.fechaPubli = fechaPubli;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getVisualizaciones() {
		return visualizaciones;
	}

	public void setVisualizaciones(Integer visualizaciones) {
		this.visualizaciones = visualizaciones;
	}

	@Override
	public String toString() {
		return "Resumen de producto = [id=" + id + ", titulo=" + titulo + ", precio=" + precio + ", fechaPublicacion=" + fechaPubli
				+ ", categoria=" + categoria + ", visualizaciones=" + visualizaciones + "]";
	}
	
}
