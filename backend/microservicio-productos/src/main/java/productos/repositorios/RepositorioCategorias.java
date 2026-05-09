package productos.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import productos.modelo.Categoria;

@NoRepositoryBean
public interface RepositorioCategorias extends CrudRepository<Categoria, String> {

	List<Categoria> findAll();
}
