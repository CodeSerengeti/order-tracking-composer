package skyglass.composer.inmemory;

import java.util.function.Consumer;

import skyglass.composer.consumer.common.SubscriberIdAndMessage;

public class MessageHandlerWithSubscriberId {

	private String subscriber;

	private Consumer<SubscriberIdAndMessage> messageHandler;

	public MessageHandlerWithSubscriberId(String subscriber, Consumer<SubscriberIdAndMessage> messageHandler) {
		this.subscriber = subscriber;
		this.messageHandler = messageHandler;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public Consumer<SubscriberIdAndMessage> getMessageHandler() {
		return messageHandler;
	}
}
