package modelo;

import java.time.LocalDateTime;

public class ProductoRes {

	private String titulo;
	private Float precio;
	private LocalDateTime fechaPubli;
	private Categoria categoria;
	private Integer visualizaciones;
	
	public ProductoRes(String titulo, Float precio, LocalDateTime fechaPubli, Categoria categoria, Integer visualizaciones) {
		this.titulo = titulo;
		this.precio = precio;
		this.fechaPubli = fechaPubli;
		this.categoria = categoria;
		this.visualizaciones = visualizaciones;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
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
		return "Producto [titulo=" + titulo + ", precio=" + precio + ", fechaPubli=" + fechaPubli + ", categoria="
				+ categoria + ", visualizaciones=" + visualizaciones + "]";
	}
}
