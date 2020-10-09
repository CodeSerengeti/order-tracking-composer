package skyglass.composer.sagas.orchestration;

import skyglass.composer.messaging.common.Message;

public interface SagaDefinition<Data> {

	SagaActions<Data> start(Data sagaData);

	SagaActions<Data> handleReply(String currentState, Data sagaData, Message message);

}
