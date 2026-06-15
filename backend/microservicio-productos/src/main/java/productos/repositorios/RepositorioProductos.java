package productos.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import productos.modelo.Categoria;
import productos.modelo.Producto;

@NoRepositoryBean
public interface RepositorioProductos extends CrudRepository<Producto, String> {

	List<Producto> findAll();

	Page<Producto> findAll(Pageable pageable);

	List<Producto> getByFecha(Integer mes, Integer year);

	List<Producto> getByCategorias(String idCategoria, List<Categoria> categorias);

	Page<Producto> getByVendedor(String idVendedor, Pageable pageable);
}
