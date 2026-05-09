package productos.adaptadores;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import productos.puertos.ManejadorEventos;
import productos.rabbitmq.RabbitMQConfig;
import productos.repositorios.EntidadNoEncontrada;
import productos.repositorios.RepositorioException;

@Component
public class ConsumidorEventos {
	
	@Autowired
	private ManejadorEventos manejadorEventos;
	
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleEvent(Map<String, String> mensaje) throws RepositorioException, EntidadNoEncontrada {
    	
        System.out.println("Mensaje recibido en Productos: " + mensaje);
        
        if (mensaje.get("tipo").equals("compraventa-creada")) {
        	this.manejadorEventos.compraventaCreada(mensaje.get("idProducto"));
        } else if (mensaje.get("tipo").equals("usuario-creado")) {
        	this.manejadorEventos.usuarioCreado(mensaje.get("id"), mensaje.get("nombre"), mensaje.get("apellidos"), mensaje.get("email"));
        } else if (mensaje.get("tipo").equals("usuario-modificado")) {
        	this.manejadorEventos.usuarioModificado(mensaje.get("id"), mensaje.get("nombre"), mensaje.get("apellidos"));
        }
        
    }
}
