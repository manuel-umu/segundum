package compraventas.servicios;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import compraventas.dto.ProductoInfoDTO;
import compraventas.dto.UsuariosInfoDTO;
import compraventas.eventos.EventoCompraventaCreada;
import compraventas.modelo.Compraventa;
import compraventas.puertos.IClienteProductos;
import compraventas.puertos.IClienteUsuarios;
import compraventas.puertos.PublicadorEventos;
import compraventas.repositorios.RepositorioCompraventas;

@Service
public class ServicioCompraventas implements IServicioCompraventas {
	
	private RepositorioCompraventas repo;
	
	@Autowired
	private PublicadorEventos publicador;
	
	@Autowired
	private IClienteUsuarios clienteUsuarios;
		
	@Autowired
	private IClienteProductos clienteProductos;
	
	@Autowired
	public ServicioCompraventas(RepositorioCompraventas repo) {
		this.repo = repo;
	}

	public String registrarCompraventa(String idProducto, String idComprador) throws IOException {
		if (idProducto == null || idProducto.isEmpty())
			throw new IllegalArgumentException("idProducto: no debe ser nulo ni vacio");
		
		if (idComprador == null || idComprador.isEmpty())
			throw new IllegalArgumentException("idComprador: no debe ser nulo ni vacio");
		
		ProductoInfoDTO producto = clienteProductos.getProducto(idProducto);
		
		if (producto == null)
			throw new IllegalArgumentException("producto: no debe ser nulo");
		
		if (producto.isVendido())
			throw new IllegalArgumentException("El producto ya ha sido vendido");
		
		UsuariosInfoDTO comprador = clienteUsuarios.getNombreUsuario(idComprador);
		UsuariosInfoDTO vendedor = clienteUsuarios.getNombreUsuario(producto.getIdVendedor());
		
		
		Compraventa compraventa = new Compraventa();
		compraventa.setIdProducto(idProducto);
		compraventa.setTitulo(producto.getTitulo());
		compraventa.setPrecio(producto.getPrecio());
		compraventa.setRecogida(producto.getRecogida());
		
		compraventa.setIdComprador(idComprador);
		compraventa.setNombreComprador(comprador.getNombre());
		compraventa.setIdVendedor(producto.getIdVendedor());
		compraventa.setNombreVendedor(vendedor.getNombre());
		compraventa.setFecha(LocalDateTime.now());

		repo.save(compraventa);

		EventoCompraventaCreada evento = new EventoCompraventaCreada(compraventa.getId(),
				compraventa.getIdProducto(), compraventa.getIdVendedor(), compraventa.getIdComprador());
		
		publicador.publicarEvento(evento);
		
		return compraventa.getId();
		
	}

	public List<Compraventa> recuperarCompras(String idUsuario) {
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		return repo.findByIdComprador(idUsuario);
	}

	public List<Compraventa> recuperarVentas(String idUsuario) {
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		return repo.findByIdVendedor(idUsuario);
	}

	public List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor) {
		if (idComprador == null || idComprador.isEmpty())
			throw new IllegalArgumentException("idComprador: no debe ser nulo ni vacio");
		if (idVendedor == null || idVendedor.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		return repo.findByIdCompradorAndIdVendedor(idComprador, idVendedor);
	}

}
