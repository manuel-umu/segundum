package compraventas.dto;

import java.time.LocalDateTime;

import compraventas.modelo.Compraventa;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO con los datos de una compraventa")
public class CompraventaOutputDTO {

	@Schema(description = "Identificador del producto comprado")
	private String idProducto;
	@Schema(description = "Título del producto", example = "Bicicleta de montaña")
	private String titulo;
	@Schema(description = "Precio de venta en euros", example = "150.0")
	private Float precio;
	@Schema(description = "Lugar de recogida del producto")
	private Object recogida;
	@Schema(description = "Identificador del vendedor")
	private String idVendedor;
	@Schema(description = "Nombre completo del vendedor", example = "Ana García")
	private String nombreVendedor;
	@Schema(description = "Identificador del comprador")
	private String idComprador;
	@Schema(description = "Nombre completo del comprador", example = "Juan López")
	private String nombreComprador;
	@Schema(description = "Fecha y hora en que se realizó la compraventa")
	private LocalDateTime fecha;

	public CompraventaOutputDTO() {
	}

	public CompraventaOutputDTO(String idProducto, String titulo, Float precio, Object recogida, String idVendedor,
			String nombreVendedor, String idComprador, String nombreComprador, LocalDateTime fecha) {
		this.idProducto = idProducto;
		this.titulo = titulo;
		this.precio = precio;
		this.recogida = recogida;
		this.idVendedor = idVendedor;
		this.nombreVendedor = nombreVendedor;
		this.idComprador = idComprador;
		this.nombreComprador = nombreComprador;
		this.fecha = fecha;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
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

	public Object getRecogida() {
		return recogida;
	}

	public void setRecogida(String recogida) {
		this.recogida = recogida;
	}

	public String getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public String getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(String idComprador) {
		this.idComprador = idComprador;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public static CompraventaOutputDTO toDto(Compraventa compraventa) {
		if (compraventa == null) {
			return null;
		}

		return new CompraventaOutputDTO(compraventa.getIdProducto(), compraventa.getTitulo(), compraventa.getPrecio(),
				compraventa.getRecogida(), compraventa.getIdVendedor(), compraventa.getNombreVendedor(),
				compraventa.getIdComprador(), compraventa.getNombreComprador(), compraventa.getFecha());
	}

}
