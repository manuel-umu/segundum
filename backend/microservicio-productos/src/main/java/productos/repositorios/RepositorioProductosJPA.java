package productos.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import productos.modelo.Categoria;
import productos.modelo.Producto;

@Repository
public interface RepositorioProductosJPA extends RepositorioProductos, JpaRepository<Producto, String> {

	@Query("SELECT p FROM Producto p WHERE MONTH(p.fechaPubli) = :mes AND YEAR(p.fechaPubli) = :year")
	List<Producto> getByFecha(@Param("mes") Integer mes, @Param("year") Integer year);

	@Query("SELECT p FROM Producto p WHERE p.categoria IN :categorias OR p.categoria.id = :idCategoria")
	List<Producto> getByCategorias(@Param("idCategoria") String idCategoria,
			@Param("categorias") List<Categoria> categorias);

	@Query("SELECT p FROM Producto p WHERE p.vendedor.id = :idVendedor")
	Page<Producto> getByVendedor(@Param("idVendedor") String idVendedor, Pageable pageable);
}
