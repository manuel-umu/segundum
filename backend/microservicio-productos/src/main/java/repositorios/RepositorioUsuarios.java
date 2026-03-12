package repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import modelo.Usuario;

@Repository
public interface RepositorioUsuarios extends JpaRepository<Usuario, String> {
	
	List<Usuario> findByEmail(String email);
	
	default boolean isRegistrado(String email) {
		return !findByEmail(email).isEmpty();
	}
}
