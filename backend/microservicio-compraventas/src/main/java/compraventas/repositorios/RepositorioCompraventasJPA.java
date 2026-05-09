package compraventas.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import compraventas.modelo.Compraventa;

@Repository
public interface RepositorioCompraventasJPA extends RepositorioCompraventas, MongoRepository<Compraventa, String> {
}
