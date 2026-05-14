package compraventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO con la información de un vendedor.")
public class VendedorInfoDTO {

	@Schema(description = "ID de un vendedor.", example = "e5f6g7h8-...")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
