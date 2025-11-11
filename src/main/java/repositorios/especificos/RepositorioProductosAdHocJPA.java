package repositorios.especificos;

import java.util.List;
import java.util.stream.Collectors;

import modelo.Categoria;
import modelo.Producto;
import repositorios.RepositorioException;

public class RepositorioProductosAdHocJPA extends RepositorioProductosJPA implements RepositorioProductosAdHoc {

	public List<Producto> getByFecha(Integer mes, Integer year) throws RepositorioException {
		return getAll().stream()
				.filter(p -> mes.equals(p.getFechaPubli().getMonthValue()) && year.equals(p.getFechaPubli().getYear()))
				.collect(Collectors.toList());
	}

	public List<Producto> getByCategorias(String idCategoria, List<Categoria> categorias) throws RepositorioException {
		// En este stream se realiza el siguiente filtro: O la categoría a la que
		// pertenece el producto es una de las subcategorías
		// O el id es exactamente el mismo del id proporcionado
		return getAll().stream()
				.filter(p -> (categorias.contains(p.getCategoria()) || p.getCategoria().getId().equals(idCategoria)))
				.collect(Collectors.toList());
	}
}
