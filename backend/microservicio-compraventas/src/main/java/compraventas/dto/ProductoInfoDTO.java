package compraventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO con la información de un producto.")
public class ProductoInfoDTO {

	@Schema(description = "Identificador del producto.", example = "a1b2c3d4-...")
	private String id;
	@Schema(description = "Descripción del producto.", example = "Bici de montaña.")
	private String titulo;
	@Schema(description = "Precio del producto.", example = "150.0")
	private Float precio;
	@Schema(description = "Identificador del producto a comprar", example = "Bici en muy buen estado.")
	private Object recogida;
	@Schema(description = "DTO de información del vendedor del producto")
	private VendedorInfoDTO vendedor;
	@Schema(description = "Booleano sobre si el producto está vendido o no", example = "true")
	private boolean vendido;
	
	public ProductoInfoDTO() {
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

	public Object getRecogida() {
		return recogida;
	}

	public void setRecogida(Object recogida) {
		this.recogida = recogida;
	}

	public VendedorInfoDTO getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorInfoDTO vendedor) {
		this.vendedor = vendedor;
	}
	
	public String getIdVendedor() {
		return vendedor.getId();
	}

	public boolean isVendido() {
		return vendido;
	}

	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	
}
