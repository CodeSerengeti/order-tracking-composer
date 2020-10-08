package skyglass.composer.spring.consumer.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.consumer.common.DecoratedMessageHandlerFactory;
import skyglass.composer.consumer.common.DuplicateDetectingMessageHandlerDecorator;
import skyglass.composer.consumer.common.DuplicateMessageDetector;
import skyglass.composer.consumer.common.MessageHandlerDecorator;
import skyglass.composer.consumer.common.PrePostHandlerMessageHandlerDecorator;
import skyglass.composer.consumer.common.PrePostReceiveMessageHandlerDecorator;
import skyglass.composer.messaging.common.MessageInterceptor;

@Configuration
public class TramConsumerBaseCommonConfiguration {

	@Autowired(required = false)
	private MessageInterceptor[] messageInterceptors = new MessageInterceptor[0];

	@Bean
	public DecoratedMessageHandlerFactory subscribedMessageHandlerChainFactory(List<MessageHandlerDecorator> decorators) {
		return new DecoratedMessageHandlerFactory(decorators);
	}

	@Bean
	public PrePostReceiveMessageHandlerDecorator prePostReceiveMessageHandlerDecoratorDecorator() {
		return new PrePostReceiveMessageHandlerDecorator(messageInterceptors);
	}

	@Bean
	public DuplicateDetectingMessageHandlerDecorator duplicateDetectingMessageHandlerDecorator(DuplicateMessageDetector duplicateMessageDetector) {
		return new DuplicateDetectingMessageHandlerDecorator(duplicateMessageDetector);
	}

	@Bean
	public PrePostHandlerMessageHandlerDecorator prePostHandlerMessageHandlerDecorator() {
		return new PrePostHandlerMessageHandlerDecorator(messageInterceptors);
	}
}
