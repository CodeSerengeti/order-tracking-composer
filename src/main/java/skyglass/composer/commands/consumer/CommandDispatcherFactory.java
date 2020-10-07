package skyglass.composer.commands.consumer;

import skyglass.composer.messaging.consumer.MessageConsumer;
import skyglass.composer.messaging.producer.MessageProducer;

public class CommandDispatcherFactory {

	private final MessageConsumer messageConsumer;

	private final MessageProducer messageProducer;

	public CommandDispatcherFactory(MessageConsumer messageConsumer,
			MessageProducer messageProducer) {
		this.messageConsumer = messageConsumer;
		this.messageProducer = messageProducer;
	}

	public CommandDispatcher make(String commandDispatcherId,
			CommandHandlers commandHandlers) {
		return new CommandDispatcher(commandDispatcherId, commandHandlers, messageConsumer, messageProducer);
	}
}
