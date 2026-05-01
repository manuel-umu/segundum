package productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad LugarRecogida")
public class LugarRecogidaDTO {
	private String descripcion;
	private Double longitud;
	private Double latitud;

	public LugarRecogidaDTO(String descripcion, Double longitud, Double latitud) {
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Double getLongitud() {
		return longitud;
	}

	public Double getLatitud() {
		return latitud;
	}
}
