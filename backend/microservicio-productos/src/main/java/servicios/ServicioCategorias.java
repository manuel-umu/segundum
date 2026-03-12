package servicios;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import modelo.Categoria;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import repositorios.RepositorioCategorias;

@Service
public class ServicioCategorias implements IServicioCategorias {

	@Autowired
	private RepositorioCategorias repositorio;

	@Override
	public void cargarCategoria(String ruta) throws RepositorioException {
		// En Spring Data este método ya no cargará desde XML hacia DB localmente
		// o deberíamos mover el Unmarshaller aquí. Se omite para la migración básica.
	}

	@Override
	public LinkedList<Categoria> recuperarCategoriaRaiz() throws RepositorioException {
		// Sabemos que la ruta de una categoría raíz solo tiene dos separadores "|", por
		// tanto si buscamos en todas las categorias formamos una lista con ellas
		int cont_barras = 0;
		LinkedList<Categoria> padres = new LinkedList<>();
		List<Categoria> todas = repositorio.findAll();
		// Recorremos cada una de ellas y extraemos su ruta
		for (Categoria categoria : todas) {
			String ruta = categoria.getRuta();
			if (ruta == null) {
				continue;
			}
			// Hacemos la resta de la longitud de esta cadena con y sin "|"
			cont_barras = ruta.length() - ruta.replace("|", "").length();
			// Si es 2, es padre y por tanto la añadimos a la lista
			if (cont_barras == 2) {
				padres.add(categoria);
			}
		}
		return padres;
	}

	@Override
	public LinkedList<Categoria> recuperarDescCategoria(String id) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		Categoria padre = getCategoria(id);
		LinkedList<Categoria> hijos = new LinkedList<>();
		getSubcategoriasRecursivo(hijos, padre);
		return hijos;
	}

	private void getSubcategoriasRecursivo(LinkedList<Categoria> hijos, Categoria categoria) {
		for (Categoria hijo : categoria.getSubcategorias()) {
			hijos.add(hijo);
			// Además los hijos añaden a sus hijos a la lista y así hasta el final del árbol
			getSubcategoriasRecursivo(hijos, hijo);
		}
	}

	@Override
	public Categoria getCategoria(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.findById(id).orElseThrow(() -> new EntidadNoEncontrada(id + " no existe en el repositorio"));
	}
}
