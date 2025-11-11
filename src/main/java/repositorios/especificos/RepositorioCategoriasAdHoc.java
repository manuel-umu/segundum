package repositorios.especificos;

import java.util.LinkedList;
import java.util.List;

import modelo.Categoria;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import repositorios.RepositorioString;

public interface RepositorioCategoriasAdHoc extends RepositorioString<Categoria> {

	public void cargarCategoria(String ruta) throws RepositorioException;

	public LinkedList<Categoria> getBySubcategorias(String id) throws RepositorioException, EntidadNoEncontrada;

}
