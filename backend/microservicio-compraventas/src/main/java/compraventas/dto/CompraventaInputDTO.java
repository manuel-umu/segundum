package compraventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de entrada para registrar una compraventa")
public class CompraventaInputDTO {

	@Schema(description = "Identificador del producto a comprar", required = true, example = "a1b2c3d4-...")
	private String idProducto;

	@Schema(description = "Identificador del comprador", required = true, example = "e5f6g7h8-...")
	private String idComprador;

	public CompraventaInputDTO() {
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getIdComprador() {
		return idComprador;
	}

	public void setIdComprador(String idComprador) {
		this.idComprador = idComprador;
	}
}
