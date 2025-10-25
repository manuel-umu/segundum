package servicios;

import java.util.LinkedList;

import enumerados.EnumEstado;
import modelo.Producto;

public interface IServicioProductos {
	
	public String altaProducto(String titulo, String descripcion, float precio, EnumEstado estado, String idCategoria, boolean envioDispo, String idVendedor);
	
	public void asignarRecogida(String id, double longitud, double latitud, String descLugar);
	
	public void modificarProducto(String id, float precio, String descripcion);

	public void añadirVisualizacion(String id);
	
	public LinkedList<Producto> historialMes(int mes, int año);
	
	public LinkedList<Producto> buscarProductos(String idCategoria, String texto, EnumEstado estado, float precioMax);
}
