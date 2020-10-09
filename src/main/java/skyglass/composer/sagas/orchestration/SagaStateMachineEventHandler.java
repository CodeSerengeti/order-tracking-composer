package skyglass.composer.sagas.orchestration;

import skyglass.composer.events.common.DomainEvent;
import skyglass.composer.events.subscriber.DomainEventEnvelope;

public interface SagaStateMachineEventHandler<Data, EventClass extends DomainEvent> {

	SagaActions<Data> apply(Data data, DomainEventEnvelope<EventClass> event);

}
