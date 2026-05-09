package productos.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import productos.enumerados.EnumEstado;
import productos.modelo.Categoria;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;

@Schema(description = "DTO de salida con los datos de un producto")
public class ProductoResDTO {

	@Schema(description = "Identificador único del producto")
	private String id;
	@Schema(description = "Título del producto", example = "Bicicleta de montaña")
	private String titulo;
	@Schema(description = "Descripción detallada del producto", example = "Bicicleta en buen estado, talla M")
	private String descripcion;
	@Schema(description = "Precio del producto en euros", example = "150.0")
	private Float precio;
	@Schema(description = "Estado del producto", example = "NUEVO")
	private EnumEstado estado;
	@Schema(description = "Fecha de publicación del producto")
	private LocalDateTime fechaPubli;
	@Schema(description = "Categoría a la que pertenece el producto")
	private Categoria categoria;
	@Schema(description = "Número de visualizaciones del producto", example = "42")
	private Integer visualizaciones;
	@Schema(description = "Indica si el envío está disponible", example = "true")
	private Boolean envioDispo;
	@Schema(description = "Lugar de recogida del producto")
	private LugarRecogida recogida;
	@Schema(description = "Vendedor del producto")
	private UsuarioDTO vendedor;
	@Schema(description = "Indica si el producto ha sido vendido", example = "false")
	private Boolean vendido;

	public ProductoResDTO() {
	}

	public ProductoResDTO(String id, String titulo, String descripcion, Float precio, EnumEstado estado,
			LocalDateTime fechaPubli, Categoria categoria, Integer visualizaciones, Boolean envioDispo,
			LugarRecogida recogida, UsuarioDTO vendedor, Boolean vendido) {
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
		this.vendido = vendido;
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

	public void setPrecio(Float precio) {
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

	public Integer getVisualizaciones() {
		return visualizaciones;
	}

	public void setVisualizaciones(Integer visualizaciones) {
		this.visualizaciones = visualizaciones;
	}

	public Boolean getEnvioDispo() {
		return envioDispo;
	}

	public void setEnvioDispo(Boolean envioDispo) {
		this.envioDispo = envioDispo;
	}

	public LugarRecogida getRecogida() {
		return recogida;
	}

	public void setRecogida(LugarRecogida recogida) {
		this.recogida = recogida;
	}

	public UsuarioDTO getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioDTO vendedor) {
		this.vendedor = vendedor;
	}

	public Boolean getVendido() {
		return vendido;
	}

	public void setVendido(Boolean vendido) {
		this.vendido = vendido;
	}

	public static ProductoResDTO toDto(Producto producto) {
		if (producto == null)
			return null;
		UsuarioDTO vendedor = UsuarioDTO.toDto(producto.getVendedor());
		return new ProductoResDTO(producto.getId(), producto.getTitulo(), producto.getDescripcion(),
				producto.getPrecio(), producto.getEstado(), producto.getFechaPubli(), producto.getCategoria(),
				producto.getVisualizaciones(), producto.isEnvioDispo(), producto.getRecogida(), vendedor,
				producto.getVendido());
	}
}
