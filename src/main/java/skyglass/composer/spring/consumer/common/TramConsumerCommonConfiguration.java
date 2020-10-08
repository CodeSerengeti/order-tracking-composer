package skyglass.composer.spring.consumer.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.consumer.common.DecoratedMessageHandlerFactory;
import skyglass.composer.consumer.common.MessageConsumerImpl;
import skyglass.composer.consumer.common.MessageConsumerImplementation;
import skyglass.composer.messaging.common.ChannelMapping;
import skyglass.composer.messaging.consumer.MessageConsumer;

@Configuration
@Import(TramConsumerBaseCommonConfiguration.class)
public class TramConsumerCommonConfiguration {

	@Bean
	public MessageConsumer messageConsumer(MessageConsumerImplementation messageConsumerImplementation,
			ChannelMapping channelMapping,
			DecoratedMessageHandlerFactory decoratedMessageHandlerFactory) {
		return new MessageConsumerImpl(channelMapping, messageConsumerImplementation, decoratedMessageHandlerFactory);
	}
}
