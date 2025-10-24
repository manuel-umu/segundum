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

public class ServicioCategorias implements IServicioCategoria {
	private Repositorio<Categoria, String> repositorio = FactoriaRepositorios.getRepositorio(Categoria.class);
	
	@Override
	public void cargarCategoria(String ruta) throws RepositorioException{
		// Control de integridad de los datos
		if (ruta == null || ruta.isEmpty())
			throw new IllegalArgumentException("ruta: no debe ser nulo ni vacio");
		try {
			// Proceso de desempaquetado con Unmarshaller
			JAXBContext contexto = JAXBContext.newInstance(Categoria.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			Categoria categoria = (Categoria) unmarshaller.unmarshal(new File(ruta));
			// Comprobamos que la categoria no es vacía
			if (categoria.getId() != null) {
				String id = categoria.getId();
				// Comprobación de si existe ya la categoría en el repo, solo añadimos si no estuviese
				if (repositorio.getById(id) == null) {
					repositorio.add(categoria);
				}
			}
		} catch (Exception e) {
			throw new RepositorioException("Problema en el desempaquetado", e);
		}
	}
	
	@Override
	public void modificarCategoria(String id, String descripcion) throws RepositorioException, EntidadNoEncontrada{
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
	public LinkedList<Categoria> recuperarCategoriaRaiz() throws RepositorioException{
		// Sabemos que la ruta de una categoría raíz solo tiene dos separadores "|", por tanto si buscamos en todas las categorias formamos una lista con ellas
		int cont_barras = 0;
		LinkedList<Categoria> padres = new LinkedList<>();
		List<Categoria> todas = repositorio.getAll();
		// Recorremos cada una de ellas y extraemos su ruta
		for (Categoria categoria : todas) {
			String ruta = categoria.getRuta();
			// Hacemos la resta de la longitud de esta cadena con y sin "|"
			cont_barras = ruta.length() - ruta.replace("|", "").length();
			// Si es 2, es padre y por tanto la añadimos a la lista
			if(cont_barras == 2) {
				padres.add(categoria);
			}
		}
		return padres;
	}
	
	@Override
	public LinkedList<Categoria> recuperarDescCategoria(String id) throws RepositorioException, EntidadNoEncontrada{
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		// Creamos la lista de hijos a devolver
		LinkedList<Categoria> hijos = new LinkedList<>();
		Categoria categoria = repositorio.getById(id);
		// Utilizamos getSubcategorias y de manera recurrente con un método auxiliar vamos añadiendo las subcategorias de subcategorias
		añadirDescRecursivo(hijos, categoria);
		return hijos;
	}
	
	public void añadirDescRecursivo(LinkedList<Categoria> hijos, Categoria categoria) {
		// Recorremos las subcategorias y añadimos cada hijo a la lista
		for (Categoria hijo : categoria.getSubcategorias()) {
			hijos.add(hijo);
			// Además los hijos añaden a sus hijos a la lista y así hasta el final del árbol
			añadirDescRecursivo(hijos, hijo);
		}
	}
}
