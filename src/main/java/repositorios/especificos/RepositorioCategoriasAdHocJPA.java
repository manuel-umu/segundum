package repositorios.especificos;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import modelo.Categoria;
import modelo.Producto;
import modelo.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioCategoriasAdHocJPA extends RepositorioCategoriasJPA implements RepositorioCategoriasAdHoc {

	public List<Categoria> getBySubcategorias(String id) throws RepositorioException, EntidadNoEncontrada {
		Categoria padre = getById(id);
		String rutaPadre = padre.getRuta();
		String consulta = "SELECT c " + "FROM Categoria c " + "WHERE c.ruta LIKE :rutaPadre";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("rutaPadre", rutaPadre);
		return getByJPQL(consulta, parametros, Categoria.class);
	}
}
