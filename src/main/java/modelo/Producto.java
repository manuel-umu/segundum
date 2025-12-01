package modelo;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import enumerados.EnumEstado;
import repositorios.Identificable;
import utils.LocalDateTimeAdapter;

@Entity
@Table(name = "producto")
public class Producto implements Identificable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	private String titulo;
	@Lob
	private String descripcion;
	private Float precio;
	@Enumerated(EnumType.STRING)
	private EnumEstado estado;
	private LocalDateTime fechaPubli;
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	private Integer visualizaciones;
	private Boolean envioDispo;
	@Embedded
	private LugarRecogida recogida;
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor;

	public Producto() {
	}

	public Producto(String titulo, String descripcion, Float precio, EnumEstado estado, Categoria categoria,
			Boolean envioDispo, Usuario vendedor, LugarRecogida lg) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
		this.fechaPubli = LocalDateTime.now();
		this.categoria = categoria;
		this.visualizaciones = 0;
		this.envioDispo = envioDispo;
		this.vendedor = vendedor;
		this.recogida = lg;
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

	public Float getPrecio() {
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

	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
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
