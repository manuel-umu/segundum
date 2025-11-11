package servicios;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import modelo.Categoria;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.Repositorio;
import repositorios.RepositorioException;
import repositorios.especificos.RepositorioCategoriasAdHoc;

public class ServicioCategorias implements IServicioCategorias {
	private RepositorioCategoriasAdHoc repositorio = FactoriaRepositorios.getRepositorio(Categoria.class);

	@Override
	public void cargarCategoria(String ruta) throws RepositorioException {
		// Control de integridad de los datos
		if (ruta == null || ruta.isEmpty())
			throw new IllegalArgumentException("ruta: no debe ser nulo ni vacio");
		repositorio.cargarCategoria(ruta);
	}

	@Override
	public void modificarCategoria(String id, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if (descripcion == null)
			throw new IllegalArgumentException("descripcion: no debe ser nulo");
		// Hacemos set con la nueva descripcion de existir la categoría en el repo
		if (repositorio.getById(id) != null) {
			Categoria categoria = repositorio.getById(id);
			categoria.setDescripcion(descripcion);
			repositorio.update(categoria);
		}
	}

	@Override
	public LinkedList<Categoria> recuperarCategoriaRaiz() throws RepositorioException {
		// Sabemos que la ruta de una categoría raíz solo tiene dos separadores "|", por
		// tanto si buscamos en todas las categorias formamos una lista con ellas
		int cont_barras = 0;
		LinkedList<Categoria> padres = new LinkedList<>();
		List<Categoria> todas = repositorio.getAll();
		// Recorremos cada una de ellas y extraemos su ruta
		for (Categoria categoria : todas) {
			String ruta = categoria.getRuta();
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
		return repositorio.getBySubcategorias(id);
	}
}
