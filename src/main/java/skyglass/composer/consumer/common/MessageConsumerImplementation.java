package skyglass.composer.consumer.common;

import java.util.Set;

import skyglass.composer.messaging.consumer.MessageHandler;
import skyglass.composer.messaging.consumer.MessageSubscription;

public interface MessageConsumerImplementation {
	MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler);

	String getId();

	void close();
}
