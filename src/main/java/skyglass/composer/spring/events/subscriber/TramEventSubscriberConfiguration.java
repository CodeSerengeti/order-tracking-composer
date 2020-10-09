package skyglass.composer.spring.events.subscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.events.common.DomainEventNameMapping;
import skyglass.composer.events.subscriber.DomainEventDispatcherFactory;
import skyglass.composer.messaging.consumer.MessageConsumer;

@Configuration
public class TramEventSubscriberConfiguration {

	@Bean
	public DomainEventDispatcherFactory domainEventDispatcherFactory(MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
		return new SpringDomainEventDispatcherFactory(messageConsumer, domainEventNameMapping);
	}
}
