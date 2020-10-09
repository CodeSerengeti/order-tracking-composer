package skyglass.composer.events.subscriber;

import skyglass.composer.events.common.DomainEvent;
import skyglass.composer.messaging.common.Message;

public interface DomainEventEnvelope<T extends DomainEvent> {
	String getAggregateId();

	Message getMessage();

	String getAggregateType();

	String getEventId();

	T getEvent();
}
