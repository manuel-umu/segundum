package compraventas.adaptadores;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import compraventas.eventos.Evento;
import compraventas.puertos.PublicadorEventos;
import compraventas.rabbitmq.RabbitMQConfig;

@Component
public class PublicadorEventosRabbitMQ implements PublicadorEventos{
	@Autowired
    private RabbitTemplate rabbitTemplate;

	@Override
	public void publicarEvento(Evento evento)  {
		rabbitTemplate.convertAndSend(
		          RabbitMQConfig.EXCHANGE_NAME, 
		          RabbitMQConfig.ROUTING_KEY + evento.getTipo(), 
		          evento);
		
	}
}
