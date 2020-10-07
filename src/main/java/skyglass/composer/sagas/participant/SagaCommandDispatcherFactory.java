package skyglass.composer.sagas.participant;

import skyglass.composer.commands.consumer.CommandHandlers;
import skyglass.composer.messaging.consumer.MessageConsumer;
import skyglass.composer.messaging.producer.MessageProducer;
import skyglass.composer.sagas.common.SagaLockManager;

public class SagaCommandDispatcherFactory {

	private final MessageConsumer messageConsumer;

	private final MessageProducer messageProducer;

	private final SagaLockManager sagaLockManager;

	public SagaCommandDispatcherFactory(MessageConsumer messageConsumer, MessageProducer messageProducer, SagaLockManager sagaLockManager) {
		this.messageConsumer = messageConsumer;
		this.messageProducer = messageProducer;
		this.sagaLockManager = sagaLockManager;
	}

	public SagaCommandDispatcher make(String commandDispatcherId, CommandHandlers target) {
		return new SagaCommandDispatcher(commandDispatcherId, target, messageConsumer, messageProducer, sagaLockManager);
	}
}
