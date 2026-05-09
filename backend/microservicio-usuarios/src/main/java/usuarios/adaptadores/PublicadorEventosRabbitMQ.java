package usuarios.adaptadores;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import usuarios.eventos.Evento;
import usuarios.puertos.IPublicadorEventos;

public class PublicadorEventosRabbitMQ implements IPublicadorEventos {
	
	@Override
	public void publicarEvento(Evento evento) throws Exception {
		
		String uri = System.getenv().getOrDefault("RABBITMQ_HOST", "localhost");
	
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri(uri);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Gson gson = new Gson();
		
		String mensaje = gson.toJson(evento);

		channel.basicPublish("bus", "bus.usuarios." + evento.getTipo(), new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), mensaje.getBytes());
		
		channel.close();
		connection.close();
	}
}
