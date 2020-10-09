package skyglass.composer.spring.commands.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.commands.producer.CommandProducer;
import skyglass.composer.commands.producer.CommandProducerImpl;
import skyglass.composer.messaging.common.ChannelMapping;
import skyglass.composer.messaging.producer.MessageProducer;

@Configuration
public class TramCommandProducerConfiguration {

	@Bean
	public CommandProducer commandProducer(MessageProducer messageProducer, ChannelMapping channelMapping) {
		return new CommandProducerImpl(messageProducer);
	}

}
