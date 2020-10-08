package skyglass.composer.messaging.producer.common;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skyglass.composer.messaging.common.ChannelMapping;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.common.MessageInterceptor;
import skyglass.composer.messaging.producer.MessageProducer;

public final class MessageProducerImpl implements MessageProducer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final MessageInterceptor[] messageInterceptors;

	private final ChannelMapping channelMapping;

	private final MessageProducerImplementation implementation;

	public MessageProducerImpl(MessageInterceptor[] messageInterceptors, ChannelMapping channelMapping, MessageProducerImplementation implementation) {
		this.messageInterceptors = messageInterceptors;
		this.channelMapping = channelMapping;
		this.implementation = implementation;
	}

	private void preSend(Message message) {
		Arrays.stream(messageInterceptors).forEach(mi -> mi.preSend(message));
	}

	private void postSend(Message message, RuntimeException e) {
		Arrays.stream(messageInterceptors).forEach(mi -> mi.postSend(message, e));
	}

	@Override
	public void send(String destination, Message message) {
		prepareMessageHeaders(destination, message);
		implementation.withContext(() -> send(message));
	}

	protected void prepareMessageHeaders(String destination, Message message) {
		String id = implementation.generateMessageId();
		if (id == null) {
			if (!message.getHeader(Message.ID).isPresent())
				throw new IllegalArgumentException("message needs an id");
		} else {
			message.getHeaders().put(Message.ID, id);
		}

		message.getHeaders().put(Message.DESTINATION, channelMapping.transform(destination));

		message.getHeaders().put(Message.DATE, HttpDateHeaderFormatUtil.nowAsHttpDateString());
	}

	protected void send(Message message) {
		preSend(message);
		try {
			implementation.send(message);
			postSend(message, null);
		} catch (RuntimeException e) {
			logger.error("Sending failed", e);
			postSend(message, e);
			throw e;
		}
	}

}
