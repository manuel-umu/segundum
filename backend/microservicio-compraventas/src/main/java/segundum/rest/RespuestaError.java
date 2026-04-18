package segundum.rest;

public class RespuestaError {
	private String estado, mensaje;

	public RespuestaError(String estado, String mensaje) {
		this.estado = estado;
		this.mensaje = mensaje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "RespuestaError [estado=" + estado + ", mensaje=" + mensaje + "]";
	}	
}
