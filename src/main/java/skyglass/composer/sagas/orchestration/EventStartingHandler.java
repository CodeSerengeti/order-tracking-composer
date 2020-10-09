package skyglass.composer.sagas.orchestration;

import skyglass.composer.events.common.DomainEvent;
import skyglass.composer.events.subscriber.DomainEventEnvelope;

public interface EventStartingHandler<Data, EventClass extends DomainEvent> {
	void apply(Data data, DomainEventEnvelope<EventClass> event);
}
