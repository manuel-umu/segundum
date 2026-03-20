package segundum.eventos;

public class EventoCompraventaCreada extends Evento {
	
	private String idProducto;
	private String idComprador;
	private String idVendedor;
	
	public EventoCompraventaCreada(String id, String idProducto, String idVendedor, String idComprador) {
		super(id, "compraventa-creada");
		this.idProducto = idProducto;
		this.idVendedor = idVendedor;
		this.idComprador = idComprador;
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

	public String getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}
	
	
}
