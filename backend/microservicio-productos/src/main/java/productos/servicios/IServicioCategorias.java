package productos.servicios;

import java.util.LinkedList;

import productos.modelo.Categoria;
import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;

public interface IServicioCategorias {
	
	public void cargarCategoria(String ruta) throws RepositorioException;
	
	public LinkedList<Categoria> recuperarCategoriaRaiz() throws RepositorioException;
	
	public LinkedList<Categoria> recuperarDescCategoria(String id) throws RepositorioException, EntidadNoEncontrada;

	public Categoria getCategoria(String id) throws RepositorioException, EntidadNoEncontrada;
	
}