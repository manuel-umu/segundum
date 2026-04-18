package repositorios.especificos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import modelo.Usuario;
import repositorios.RepositorioException;
import utils.EntityManagerHelper;


public class RepositorioUsuariosAdHocJPA extends RepositorioUsuariosJPA implements RepositorioUsuariosAdHoc {

	public boolean isRegistrado(String email) throws RepositorioException {
		return !(getByEmail(email) == null);
	}
	
	public Usuario getByEmail(String email) throws RepositorioException{
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			String queryString = "SELECT e "
					+ "FROM Usuario e "
					+ "WHERE e.email = :email";
			TypedQuery<Usuario> query = em.createQuery(queryString, Usuario.class);
			query.setParameter("email", email);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			List<Usuario> resultado = query.getResultList();
			return resultado.get(0);
		} catch (RuntimeException e) {
			throw new RepositorioException("Error buscando todas las entidades por id", e);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}
}
