package segundum.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import segundum.modelo.Categoria;
import segundum.modelo.Producto;

@Repository
public interface RepositorioProductos extends JpaRepository<Producto, String> {

	@Query("SELECT p FROM Producto p WHERE MONTH(p.fechaPubli) = :mes AND YEAR(p.fechaPubli) = :year")
	List<Producto> getByFecha(@Param("mes") Integer mes, @Param("year") Integer year);

	@Query("SELECT p FROM Producto p WHERE p.categoria IN :categorias OR p.categoria.id = :idCategoria")
	List<Producto> getByCategorias(@Param("idCategoria") String idCategoria, @Param("categorias") List<Categoria> categorias);
}
