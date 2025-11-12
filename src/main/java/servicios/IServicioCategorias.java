package servicios;

import java.util.LinkedList;

import modelo.Categoria;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public interface IServicioCategorias {
	
	public void cargarCategoria(String ruta) throws RepositorioException;
	
	public void modificarCategoria(String id, String descripcion) throws RepositorioException, EntidadNoEncontrada;

	public LinkedList<Categoria> recuperarCategoriaRaiz() throws RepositorioException;
	
	public LinkedList<Categoria> recuperarDescCategoria(String id) throws RepositorioException, EntidadNoEncontrada;

	public Categoria getCategoria(String id) throws RepositorioException, EntidadNoEncontrada;
	
	public boolean comprobarAdmin();
}