package segundum.adaptadores;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import segundum.puertos.ManejadorEventos;
import segundum.rabbitmq.RabbitMQConfig;
import segundum.repositorios.EntidadNoEncontrada;
import segundum.repositorios.RepositorioException;

@Component
public class ConsumidorEventos {
	
	@Autowired
	private ManejadorEventos manejadorEventos;
	
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleEvent(Map<String, String> mensaje) throws RepositorioException, EntidadNoEncontrada {
    	
        System.out.println("Mensaje recibido en Productos: " + mensaje);
        
        if (mensaje.get("tipo").equals("compraventa-creada")) {
        	this.manejadorEventos.compraventaCreada(mensaje.get("idProducto"));
        }
        
    }
}
