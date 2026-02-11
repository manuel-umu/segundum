package repositorios.especificos;

import java.util.List;

import modelo.Categoria;
import modelo.Producto;
import repositorios.RepositorioException;
import repositorios.RepositorioString;

public interface RepositorioProductosAdHoc extends RepositorioString<Producto> {

	public List<Producto> getByFecha(Integer mes, Integer year) throws RepositorioException;

	public List<Producto> getByCategorias(String idCategoria, List<Categoria> c) throws RepositorioException;

}
