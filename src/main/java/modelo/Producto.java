package modelo;

import java.time.LocalDateTime;

import enumerados.EnumEstado;

public class Producto {
	private String id;
	private String titulo;
	private String descripcion;
	private float precio;
	private EnumEstado estado;
	private LocalDateTime fechaPubli;
	private Categoria categoria;
	private int visualizaciones;
	private boolean envioDispo;
	private LugarRecogida recogida;
	private Usuario vendedor;
	
	public Producto(String id, String titulo, String descripcion, float precio, EnumEstado estado,
			LocalDateTime fechaPubli, Categoria categoria, int visualizaciones, boolean envioDispo,
			LugarRecogida recogida, Usuario vendedor) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
		this.fechaPubli = LocalDateTime.now();
		this.categoria = categoria;
		this.visualizaciones = 0;
		this.envioDispo = envioDispo;
		this.recogida = recogida;
		this.vendedor = vendedor;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public EnumEstado getEstado() {
		return estado;
	}

	public void setEstado(EnumEstado estado) {
		this.estado = estado;
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

	public int getVisualizaciones() {
		return visualizaciones;
	}

	public void setVisualizaciones(int visualizaciones) {
		this.visualizaciones = visualizaciones;
	}

	public boolean isEnvioDispo() {
		return envioDispo;
	}

	public void setEnvioDispo(boolean envioDispo) {
		this.envioDispo = envioDispo;
	}

	public LugarRecogida getRecogida() {
		return recogida;
	}

	public void setRecogida(LugarRecogida recogida) {
		this.recogida = recogida;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", estado=" + estado + ", fechaPubli=" + fechaPubli + ", categoria=" + categoria
				+ ", visualizaciones=" + visualizaciones + ", envioDispo=" + envioDispo + ", recogida=" + recogida
				+ ", vendedor=" + vendedor + "]";
	}
	
	
}
