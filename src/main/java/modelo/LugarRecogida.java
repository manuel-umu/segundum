package modelo;

public class LugarRecogida {
	private String descripcion;
	private int longitud;
	private int latitud;
	
	public LugarRecogida(String descripcion, int longitud, int latitud) {
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getLongitud() {
		return longitud;
	}

	public int getLatitud() {
		return latitud;
	}
	
	
	
}
