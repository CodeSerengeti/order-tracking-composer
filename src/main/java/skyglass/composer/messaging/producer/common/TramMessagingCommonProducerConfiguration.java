package skyglass.composer.messaging.producer.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.messaging.common.ChannelMapping;
import skyglass.composer.messaging.common.MessageInterceptor;
import skyglass.composer.messaging.producer.MessageProducer;

@Configuration
public class TramMessagingCommonProducerConfiguration {

	@Autowired(required = false)
	private MessageInterceptor[] messageInterceptors = new MessageInterceptor[0];

	@Bean
	public MessageProducer messageProducer(ChannelMapping channelMapping, MessageProducerImplementation implementation) {
		return new MessageProducerImpl(messageInterceptors, channelMapping, implementation);
	}
}
