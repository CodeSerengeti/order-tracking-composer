package skyglass.composer.spring.events.publisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.events.common.DomainEventNameMapping;
import skyglass.composer.events.publisher.DomainEventPublisher;
import skyglass.composer.events.publisher.DomainEventPublisherImpl;
import skyglass.composer.messaging.producer.MessageProducer;

@Configuration
public class TramEventsPublisherConfiguration {

	@Bean
	public DomainEventPublisher domainEventPublisher(MessageProducer messageProducer, DomainEventNameMapping domainEventNameMapping) {
		return new DomainEventPublisherImpl(messageProducer, domainEventNameMapping);
	}
}
