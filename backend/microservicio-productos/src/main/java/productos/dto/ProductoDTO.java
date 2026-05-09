package productos.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import productos.enumerados.EnumEstado;
import productos.modelo.Categoria;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;

@Schema(description = "DTO de entrada para crear o modificar un producto")
public class ProductoDTO {
	@Schema(description = "Título del producto", example = "Bicicleta de montaña")
	@NotNull
	private String titulo;
	@Schema(description = "Descripción detallada del producto", example = "Bicicleta en buen estado, talla M")
	@NotNull
	private String descripcion;
	@Schema(description = "Precio del producto en euros", example = "150.0")
	@NotNull
	private Float precio;
	@Schema(description = "Estado del producto", example = "NUEVO")
	@NotNull
	private EnumEstado estado;
	@Schema(description = "Fecha de publicación del producto")
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fechaPubli;
	@Schema(description = "Categoría a la que pertenece el producto")
	@NotNull
	private Categoria categoria;
	@Schema(description = "Número de visualizaciones del producto", example = "0")
	private Integer visualizaciones;
	@Schema(description = "Indica si el envío está disponible", example = "true")
	@NotNull
	private Boolean envioDispo;
	@Schema(description = "Lugar de recogida del producto")
	private LugarRecogida recogida;
	@Schema(description = "Vendedor del producto")
	private UsuarioDTO vendedor;
	@Schema(description = "Indica si el producto ha sido vendido", example = "false")
	private Boolean vendido;

	public ProductoDTO() {

	}

	public ProductoDTO(String titulo, String descripcion, Float precio, EnumEstado estado, LocalDateTime fechaPubli,
			Categoria categoria, Integer visualizaciones, Boolean envioDispo, LugarRecogida recogida,
			UsuarioDTO vendedor, Boolean vendido) {
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
	
	public boolean isVendido() {
		return vendido;
	}

	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}

	@Override
	public String toString() {
		return "ProductoDTO [titulo=" + titulo + ", descripcion=" + descripcion + ", precio=" + precio + ", estado="
				+ estado + ", fechaPubli=" + fechaPubli + ", categoria=" + categoria + ", visualizaciones="
				+ visualizaciones + ", envioDispo=" + envioDispo + ", recogida=" + recogida + ", vendedor=" + vendedor
				+ "]";
	}

	public static ProductoDTO toDto(Producto producto) {
		if (producto == null) {
			return null;
		}
		UsuarioDTO usuario = UsuarioDTO.toDto(producto.getVendedor());
		return new ProductoDTO(producto.getTitulo(), producto.getDescripcion(), producto.getPrecio(),
				producto.getEstado(), producto.getFechaPubli(), producto.getCategoria(), producto.getVisualizaciones(),
				producto.isEnvioDispo(), producto.getRecogida(), usuario, producto.getVendido());
	}


}
