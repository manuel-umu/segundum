package productos.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import productos.modelo.Usuario;

@NoRepositoryBean
public interface RepositorioUsuarios extends CrudRepository<Usuario, String> {

	List<Usuario> findByEmail(String email);
}
