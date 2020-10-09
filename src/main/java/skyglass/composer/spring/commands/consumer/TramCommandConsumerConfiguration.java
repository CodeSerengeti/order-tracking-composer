package skyglass.composer.spring.commands.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.commands.consumer.CommandDispatcherFactory;
import skyglass.composer.messaging.consumer.MessageConsumer;
import skyglass.composer.messaging.producer.MessageProducer;

@Configuration
public class TramCommandConsumerConfiguration {

	@Bean
	public CommandDispatcherFactory commandDispatcherFactory(MessageConsumer messageConsumer, MessageProducer messageProducer) {
		return new CommandDispatcherFactory(messageConsumer, messageProducer);
	}
}
