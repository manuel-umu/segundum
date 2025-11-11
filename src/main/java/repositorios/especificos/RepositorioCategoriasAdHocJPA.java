package repositorios.especificos;

import java.io.File;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import modelo.Categoria;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public class RepositorioCategoriasAdHocJPA extends RepositorioCategoriasJPA implements RepositorioCategoriasAdHoc {

	public void cargarCategoria(String ruta) throws RepositorioException {
		try {
			// Proceso de desempaquetado con Unmarshaller
			JAXBContext contexto = JAXBContext.newInstance(Categoria.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			Categoria categoria = (Categoria) unmarshaller.unmarshal(new File(ruta));
			// Comprobamos que la categoria no es vacía
			if (categoria != null) {
				try {
					// Ahora comprobamos que la categoría no existe! (ya que no se puede añadir si existe según el enunciado)
					Categoria antigua = getById(categoria.getId());
					if(antigua != null) {
						System.out.println("Categoría anteriormente añadida. No se realiza la operación.");
						return;
					}
				} catch (Exception e) {
					// De no ser así añadimos
					add(categoria);
				}
			}
		} catch (Exception e) {
			throw new RepositorioException("Problema en el desempaquetado", e);
		}
	}

	public LinkedList<Categoria> getBySubcategorias(String id) throws RepositorioException, EntidadNoEncontrada {
		Categoria padre = getById(id);
		LinkedList<Categoria> hijos = new LinkedList<>();
		getSubcategoriasRecursivo(hijos, padre);
		return hijos;
	}

	public void getSubcategoriasRecursivo(LinkedList<Categoria> hijos, Categoria categoria) {
		for (Categoria hijo : categoria.getSubcategorias()) {
			hijos.add(hijo);
			// Además los hijos añaden a sus hijos a la lista y así hasta el final del árbol
			getSubcategoriasRecursivo(hijos, hijo);
		}
	}

}
