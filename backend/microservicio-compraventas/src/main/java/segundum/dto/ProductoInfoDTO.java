package segundum.dto;

public class ProductoInfoDTO {

	private String id;
	private String titulo;
	private Float precio;
	private String recogida;
	private VendedorInfoDTO vendedor;
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

	public String getRecogida() {
		return recogida;
	}

	public void setRecogida(String recogida) {
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
