package repositorios;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import modelo.Usuario;
import utils.EntityManagerHelper;

public class RepositorioUsuarios extends RepositorioJPA<Usuario> {

	@Override
	public Class<Usuario> getClase() {
		return Usuario.class;
	}

	public boolean existeUsuario(String email) throws RepositorioException {
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			final String queryString = "SELECT t FROM " + getClase().getSimpleName() + " t WHERE t.email = " + email;
			Query query = em.createQuery(queryString);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			List<Usuario> resultado = query.getResultList();
			return !resultado.isEmpty();
		} catch (RuntimeException e) {
			throw new RepositorioException("Error buscando todas las entidades de " + getClase().getSimpleName(), e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}
}
