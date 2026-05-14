package usuarios.adaptadores;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import usuarios.puertos.ManejadorEventos;
import usuarios.repositorios.EntidadNoEncontrada;
import usuarios.repositorios.RepositorioException;
import usuarios.servicios.FactoriaServicios;

@WebListener
public class ConsumidorRabbitMQ implements ServletContextListener{
	// inyección del puerto de entrada
	private final ManejadorEventos manejadorEventos = FactoriaServicios.getServicio(ManejadorEventos.class);
	
	private Connection connection;
	private Channel channel; 
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String uri = System.getenv().getOrDefault("RABBITMQ_HOST", "localhost");
		
		try {
			Thread.sleep(15000);
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri(uri);
	
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			String exchangeName = "bus";
			boolean durable = true;
			channel.exchangeDeclare(exchangeName,"topic", durable);
			
			final String queueName = "usuarios";
			final String bindingKey = "bus.compraventas.#"; 
			durable = true;
			boolean exclusive = false;
			boolean autodelete = false;
			Map<String, Object> properties = null; 
			channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);
			channel.queueBind(queueName, exchangeName, bindingKey);
			channel.queueBind(queueName, exchangeName, "bus.valoraciones.#");
			
		
			boolean autoAck = false;
			channel.basicConsume(queueName, autoAck, "usuarios-consumer", 
			  new DefaultConsumer(channel) {
			    @Override
			    public void handleDelivery(String consumerTag, Envelope envelope, 		
			    AMQP.BasicProperties properties,byte[] body) throws IOException {
			        
			       long deliveryTag = envelope.getDeliveryTag();
	
			        String contenido = new String(body);
			        System.out.println("Mensaje recibido en Usuarios" + contenido);
			        
			        JsonObject objeto = JsonParser.parseString(contenido).getAsJsonObject();
			        
			        if (objeto.get("tipo").getAsString().equals("compraventa-creada")) {
			        	
			        	// ejecutar operación del puerto ...
			        	String idVendedor = objeto.get("idVendedor").getAsString();
			        	String idComprador = objeto.get("idComprador").getAsString();
			        	
			        	try {
							manejadorEventos.compraventaCreada(idVendedor, idComprador);
						} catch (RepositorioException | EntidadNoEncontrada e) {
							e.printStackTrace();
						}
			        }
			        
			        if (objeto.get("tipo").getAsString().equals("valoracion-creada")) {
			        	
			        	// ejecutar operación del puerto ...
			        	String id = objeto.get("id").getAsString();
			        	String rol = objeto.get("rol").getAsString();
			        	double puntuacion = objeto.get("puntuacion").getAsDouble();
			        	
			        	try {
							manejadorEventos.valoracionCreada(id, rol, puntuacion);
						} catch (RepositorioException | EntidadNoEncontrada e) {
							e.printStackTrace();
						}
			        }
			        
			        
			        // Confirma el procesamiento
			        channel.basicAck(deliveryTag, false);
			    }
			});
						
			System.out.println("Usuarios esperando...");
		}
		catch(Exception e) {
			System.err.println("[ConsumidorRabbitMQ] No se pudo conectar a RabbitMQ: " + e.getMessage());
			System.err.println("[ConsumidorRabbitMQ] El servidor arranca sin consumidor de mensajes.");
		}
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		try {
		if (this.channel != null)
			this.channel.close();
		
		if (this.connection != null)
			this.connection.close();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
