package productos.servicios;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productos.modelo.Categoria;
import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import productos.repositorios.RepositorioCategorias;

@Service
public class ServicioCategorias implements IServicioCategorias {

	@Autowired
	private RepositorioCategorias repositorio;

	// Funcion de cargar categoria de aadd pero mejorada
	@Override
	public void cargarCategoria(String ruta) throws RepositorioException {
		if (ruta == null || ruta.isEmpty())
			throw new IllegalArgumentException("ruta: no debe ser nulo ni vacio");
		try {
			// Proceso de desempaquetado con Unmarshaller
			JAXBContext contexto = JAXBContext.newInstance(Categoria.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ruta);
			if(inputStream == null)
				throw new RepositorioException("No se encontró el fichero: " + ruta, null);
			Categoria categoria = (Categoria) unmarshaller.unmarshal(inputStream);
			// Comprobamos que la categoria no es vacía
			if (categoria != null) {
				if (repositorio.existsById(categoria.getId())) {
					System.err.println("Categoría ya existe: " + categoria.getId());
					return;
				}
				repositorio.save(categoria);
				System.out.println("Categoría cargada correctamente: " + categoria.getId());
			}
		} catch (JAXBException e) {
			throw new RepositorioException("Error en el formato del XML", e);
		} catch (Exception e) {
			throw new RepositorioException("Error al cargar la categoría", e);
		}
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
