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
	
}
