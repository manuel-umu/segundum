package compraventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO con la información de un usuario.")
public class UsuariosInfoDTO {

	@Schema(description = "ID de un usuario.", example = "e5f6g7h8-...")
	private String id;
	@Schema(description = "Nombre de un usuario.", example = "Juan Perez")
	private String nombre;
	
	public UsuariosInfoDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
