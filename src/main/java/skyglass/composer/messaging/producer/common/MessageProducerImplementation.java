package skyglass.composer.messaging.producer.common;

import skyglass.composer.messaging.common.Message;

public interface MessageProducerImplementation {

	void send(Message message);

	String generateMessageId();

	default void withContext(Runnable runnable) {
		runnable.run();
	}
}
