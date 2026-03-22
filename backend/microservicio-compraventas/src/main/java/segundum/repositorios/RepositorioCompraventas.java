package segundum.repositorios;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import segundum.modelo.Compraventa;

public interface RepositorioCompraventas extends MongoRepository<Compraventa, String> {
	List<Compraventa> findByIdComprador(String idComprador);
	List<Compraventa> findByIdVendedor(String idVendedor);
	List<Compraventa> findByIdAmbos(String idComprador, String idVendedor);
}