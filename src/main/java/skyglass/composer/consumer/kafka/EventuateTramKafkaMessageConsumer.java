package skyglass.composer.consumer.kafka;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.consumer.common.MessageConsumerImplementation;
import skyglass.composer.messaging.common.MessageImpl;
import skyglass.composer.messaging.consumer.MessageHandler;
import skyglass.composer.messaging.consumer.MessageSubscription;
import skyglass.composer.messaging.kafka.consumer.KafkaSubscription;
import skyglass.composer.messaging.kafka.consumer.MessageConsumerKafkaImpl;

public class EventuateTramKafkaMessageConsumer implements MessageConsumerImplementation {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private MessageConsumerKafkaImpl messageConsumerKafka;

	public EventuateTramKafkaMessageConsumer(MessageConsumerKafkaImpl messageConsumerKafka) {
		this.messageConsumerKafka = messageConsumerKafka;
	}

	@Override
	public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
		logger.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);

		KafkaSubscription subscription = messageConsumerKafka.subscribe(subscriberId,
				channels, message -> handler.accept(JSonMapper.fromJson(message.getPayload(), MessageImpl.class)));

		logger.info("Subscribed: subscriberId = {}, channels = {}", subscriberId, channels);

		return subscription::close;
	}

	@Override
	public String getId() {
		return messageConsumerKafka.getId();
	}

	@Override
	public void close() {
		logger.info("Closing consumer");

		messageConsumerKafka.close();

		logger.info("Closed consumer");
	}
}
