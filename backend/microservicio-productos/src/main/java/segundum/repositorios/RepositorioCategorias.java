package segundum.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import segundum.modelo.Categoria;

@Repository
public interface RepositorioCategorias extends JpaRepository<Categoria, String> {

}
