package productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad LugarRecogida")
public class LugarRecogidaDTO {
	@Schema(description = "Descripción del lugar de recogida", example = "Calle Espinardo numero 3")
	private String descripcion;
	@Schema(description = "Longitud geográfica del lugar", example = "-1.3004")
	private Double longitud;
	@Schema(description = "Latitud geográfica del lugar", example = "37.984")
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
