package skyglass.composer.spring.events.subscriber;

import skyglass.composer.events.common.DomainEventNameMapping;
import skyglass.composer.events.subscriber.DomainEventDispatcher;
import skyglass.composer.events.subscriber.DomainEventDispatcherFactory;
import skyglass.composer.events.subscriber.DomainEventHandlers;
import skyglass.composer.messaging.consumer.MessageConsumer;

public class SpringDomainEventDispatcherFactory extends DomainEventDispatcherFactory {

	public SpringDomainEventDispatcherFactory(MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
		super(messageConsumer, domainEventNameMapping);
	}

	@Override
	public DomainEventDispatcher make(String eventDispatcherId, DomainEventHandlers domainEventHandlers) {
		return new DomainEventDispatcher(eventDispatcherId, domainEventHandlers, messageConsumer, domainEventNameMapping);
	}
}
