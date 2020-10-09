package skyglass.composer.events.subscriber;

import skyglass.composer.events.common.DomainEventNameMapping;
import skyglass.composer.messaging.consumer.MessageConsumer;

public class DomainEventDispatcherFactory {

	protected MessageConsumer messageConsumer;

	protected DomainEventNameMapping domainEventNameMapping;

	public DomainEventDispatcherFactory(MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
		this.messageConsumer = messageConsumer;
		this.domainEventNameMapping = domainEventNameMapping;
	}

	public DomainEventDispatcher make(String eventDispatcherId, DomainEventHandlers domainEventHandlers) {
		return new DomainEventDispatcher(eventDispatcherId, domainEventHandlers, messageConsumer, domainEventNameMapping);
	}
}
