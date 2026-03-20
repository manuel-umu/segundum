package segundum.servicios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import segundum.eventos.EventoCompraventaCreada;
import segundum.modelo.Compraventa;
import segundum.puertos.PublicadorEventos;
import segundum.repositorios.RepositorioCompraventas;

@Service
public class ServicioCompraventas implements IServicioCompraventas {
	
	private RepositorioCompraventas repo;
	private PublicadorEventos publicador;
	
	@Autowired
	public ServicioCompraventas(RepositorioCompraventas repo) {
		this.repo = repo;
	}

	public String registrarCompraventa(String idProducto, String idComprador, String idVendedor) {
		if (idProducto == null || idProducto.isEmpty())
			throw new IllegalArgumentException("titulo: no debe ser nulo ni vacio");
		
		if (idComprador == null || idComprador.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");
		
		if (idVendedor == null || idVendedor.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");
		
		Compraventa compraventa = new Compraventa();
		compraventa.setIdProducto(idProducto);
		compraventa.setIdComprador(idComprador);
		compraventa.setIdVendedor(idVendedor);
		compraventa.setFecha(LocalDateTime.now());

		repo.save(compraventa);

		EventoCompraventaCreada evento = new EventoCompraventaCreada(compraventa.getId(),
				compraventa.getIdProducto(), compraventa.getIdVendedor(), compraventa.getIdComprador());
		
		publicador.publicarEvento(evento);
		
		return compraventa.getId();
		
	}

	public List<Compraventa> recuperarCompras(String idUsuario) {
		return null;
	}

	public List<Compraventa> recuperarVentas(String idUsuario) {
		return null;
	}

	public List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor) {
		return null;
	}

}
