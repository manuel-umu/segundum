package segundum.dto;

import java.time.LocalDateTime;

import segundum.modelo.Compraventa;

public class CompraventaDTO {

	private String idProducto;
	private String titulo;
	private Float precio;
	private Object recogida;
	private String idVendedor;
	private String nombreVendedor;
	private String idComprador;
	private String nombreComprador;
	private LocalDateTime fecha;

	public CompraventaDTO() {
	}

	public CompraventaDTO(String idProducto, String titulo, Float precio, Object recogida, String idVendedor,
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

	public static CompraventaDTO toDto(Compraventa compraventa) {
		if (compraventa == null) {
			return null;
		}

		return new CompraventaDTO(compraventa.getIdProducto(), compraventa.getTitulo(), compraventa.getPrecio(),
				compraventa.getRecogida(), compraventa.getIdVendedor(), compraventa.getNombreVendedor(),
				compraventa.getIdComprador(), compraventa.getNombreComprador(), compraventa.getFecha());
	}

}
