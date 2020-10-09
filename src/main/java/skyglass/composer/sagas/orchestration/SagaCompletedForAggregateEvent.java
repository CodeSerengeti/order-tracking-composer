package skyglass.composer.sagas.orchestration;

import skyglass.composer.events.common.DomainEvent;

public class SagaCompletedForAggregateEvent implements DomainEvent {
	public SagaCompletedForAggregateEvent(String sagaId) {
	}
}
