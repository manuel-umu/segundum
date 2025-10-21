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
		this.fechaPubli = fechaPubli;
		this.categoria = categoria;
		this.visualizaciones = visualizaciones;
		this.envioDispo = envioDispo;
		this.recogida = recogida;
		this.vendedor = vendedor;
	}
	
	
}
