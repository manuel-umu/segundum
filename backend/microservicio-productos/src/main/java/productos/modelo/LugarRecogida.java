package productos.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LugarRecogida {
	@Column(name="descripcionLR")
	private String descripcion;
	private Double longitud;
	private Double latitud;
	
	public LugarRecogida() {
	}
	
	public LugarRecogida(String descripcion, Double longitud, Double latitud) {
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
