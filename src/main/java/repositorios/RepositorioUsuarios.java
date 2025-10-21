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
}
