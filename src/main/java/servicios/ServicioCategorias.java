package servicios;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import controlador.Controlador;
import modelo.Categoria;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import repositorios.especificos.RepositorioCategoriasAdHoc;

public class ServicioCategorias implements IServicioCategorias {
	private RepositorioCategoriasAdHoc repositorio = FactoriaRepositorios.getRepositorio(Categoria.class);

	@Override
	public void cargarCategoria(String ruta) throws RepositorioException {
		// Control de integridad de los datos
		if (ruta == null || ruta.isEmpty())
			throw new IllegalArgumentException("ruta: no debe ser nulo ni vacio");
		if (comprobarAdmin()) {
			try {
				// Proceso de desempaquetado con Unmarshaller
				JAXBContext contexto = JAXBContext.newInstance(Categoria.class);
				Unmarshaller unmarshaller = contexto.createUnmarshaller();
				Categoria categoria = (Categoria) unmarshaller.unmarshal(new File(ruta));
				// Comprobamos que la categoria no es vacía
				if (categoria != null) {
					try {
						// Ahora comprobamos que la categoría no existe! (ya que no se puede añadir si
						// existe según el enunciado)
						Categoria antigua = repositorio.getById(categoria.getId());
						if (antigua != null) {
							System.out.println("Categoría anteriormente añadida. No se realiza la operación.");
							return;
						}
					} catch (Exception e) {
						// De no ser así añadimos
						repositorio.add(categoria);
					}
				}
			} catch (Exception e) {
				throw new RepositorioException("Problema en el desempaquetado", e);
			}
		} else {
			System.err.println("ERROR: No tienes los permisos para cargar una categoria.");
		}
	}

	@Override
	public void modificarCategoria(String id, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		if (descripcion == null)
			throw new IllegalArgumentException("descripcion: no debe ser nulo");
		// Hacemos set con la nueva descripcion de existir la categoría en el repo
		if (comprobarAdmin()) {
			Categoria categoria = repositorio.getById(id);
			categoria.setDescripcion(descripcion);
			repositorio.update(categoria);
		} else {
			System.err.println("ERROR: No tienes los permisos para modificar una categoria.");
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
	public List<Categoria> recuperarDescCategoria(String id) throws RepositorioException, EntidadNoEncontrada {
		// Control de integridad de los datos
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		return repositorio.getBySubcategorias(id);
	}

	@Override
	public Categoria getCategoria(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(id);
	}

	public boolean comprobarAdmin() {
		Usuario u = Controlador.getUnicaInstancia().getUsuarioActual();
		if (u != null)
			return u.isAdmin();
		return false;
	}
}
