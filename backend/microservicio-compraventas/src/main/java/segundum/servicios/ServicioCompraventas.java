package segundum.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import segundum.modelo.Compraventa;
import segundum.repositorios.RepositorioCompraventas;

//TODO
@Service
public class ServicioCompraventas implements IServicioCompraventas {
	private RepositorioCompraventas repo;

	public ServicioCompraventas(RepositorioCompraventas repo) {
		this.repo = repo;
	}

	public String registrarCompraventa(String idProducto, String idComprador) {
		return null;
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
