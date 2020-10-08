package skyglass.composer.messaging.kafka.spring.consumer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.messaging.kafka.basic.consumer.DefaultKafkaConsumerFactory;
import skyglass.composer.messaging.kafka.basic.consumer.KafkaConsumerFactory;

@Configuration
public class KafkaConsumerFactoryConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public KafkaConsumerFactory kafkaConsumerFactory() {
		return new DefaultKafkaConsumerFactory();
	}
}
