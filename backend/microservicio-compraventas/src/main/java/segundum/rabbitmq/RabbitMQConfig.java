package segundum.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	  public static final String EXCHANGE_NAME = "bus";
	  public static final String ROUTING_KEY = "bus.compraventas.";

	  @Bean
	  public TopicExchange exchange () {
	      return new TopicExchange(EXCHANGE_NAME);
	  }
	  
	  @Bean
	  public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	  }

	  @Bean
	  public RabbitTemplate rabbitTemplate(
	    ConnectionFactory connectionFactory, MessageConverter converter) {
		  
		
	    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(converter);
	    return rabbitTemplate;
	  }
}
