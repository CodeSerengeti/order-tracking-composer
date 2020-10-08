package skyglass.composer.spring.consumer.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.consumer.common.MessageConsumerImplementation;
import skyglass.composer.consumer.kafka.EventuateTramKafkaMessageConsumer;
import skyglass.composer.messaging.kafka.consumer.MessageConsumerKafkaImpl;
import skyglass.composer.messaging.kafka.spring.consumer.KafkaConsumerFactoryConfiguration;
import skyglass.composer.messaging.kafka.spring.consumer.MessageConsumerKafkaConfiguration;
import skyglass.composer.spring.consumer.common.TramConsumerCommonConfiguration;

@Configuration
@Import({ TramConsumerCommonConfiguration.class, MessageConsumerKafkaConfiguration.class, KafkaConsumerFactoryConfiguration.class })
public class EventuateTramKafkaMessageConsumerConfiguration {

	@Bean
	public MessageConsumerImplementation messageConsumerImplementation(MessageConsumerKafkaImpl messageConsumerKafka) {
		return new EventuateTramKafkaMessageConsumer(messageConsumerKafka);
	}
}
