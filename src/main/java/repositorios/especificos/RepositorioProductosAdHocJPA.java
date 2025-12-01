package repositorios.especificos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import modelo.Categoria;
import modelo.Producto;
import repositorios.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioProductosAdHocJPA extends RepositorioProductosJPA implements RepositorioProductosAdHoc {

	public List<Producto> getByFecha(Integer mes, Integer year) throws RepositorioException {
		String consulta = "SELECT p " + "FROM Producto p "
				+ "WHERE FUNCTION('MONTH', p.fechaPubli) = :mes AND FUNCTION('YEAR', p.fechaPubli) = :year";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("mes", mes);
		parametros.put("year", year);
		return getByJPQL(consulta, parametros, Producto.class);
	}

	public List<Producto> getByCategorias(String idCategoria, List<Categoria> categorias) throws RepositorioException {
		List<String> idsCategorias = categorias.stream().map(Categoria::getId).collect(Collectors.toList());
		String consulta = "SELECT p " + "FROM Producto p "
				+ "WHERE p.categoria.id IN :idsCategorias OR p.categoria.id = :idCategoria";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idCategoria", idCategoria);
		parametros.put("idsCategorias", idsCategorias);
		return getByJPQL(consulta, parametros, Producto.class);
	}
}
