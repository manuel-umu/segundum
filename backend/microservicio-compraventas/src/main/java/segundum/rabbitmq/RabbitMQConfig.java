package segundum.rabbitmq;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
