package compraventas.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import compraventas.modelo.Compraventa;

@NoRepositoryBean
public interface RepositorioCompraventas extends CrudRepository<Compraventa, String> {

	Page<Compraventa> findByIdComprador(String idComprador, Pageable pageable);

	Page<Compraventa> findByIdVendedor(String idVendedor, Pageable pageable);

	Page<Compraventa> findByIdCompradorAndIdVendedor(String idComprador, String idVendedor, Pageable pageable);
	
	Page<Compraventa> findAll(Pageable pageable);
}
