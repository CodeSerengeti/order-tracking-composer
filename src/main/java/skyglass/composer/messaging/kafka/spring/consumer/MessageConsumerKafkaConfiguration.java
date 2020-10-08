package skyglass.composer.messaging.kafka.spring.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import skyglass.composer.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import skyglass.composer.messaging.kafka.common.EventuateKafkaConfigurationProperties;
import skyglass.composer.messaging.kafka.consumer.MessageConsumerKafkaImpl;
import skyglass.composer.messaging.kafka.spring.basic.consumer.EventuateKafkaConsumerSpringConfigurationPropertiesConfiguration;
import skyglass.composer.messaging.kafka.spring.common.EventuateKafkaPropertiesConfiguration;

@Configuration
@Import({ EventuateKafkaPropertiesConfiguration.class, EventuateKafkaConsumerSpringConfigurationPropertiesConfiguration.class })
public class MessageConsumerKafkaConfiguration {
	@Bean
	public MessageConsumerKafkaImpl messageConsumerKafka(EventuateKafkaConfigurationProperties props,
			EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties,
			KafkaConsumerFactory kafkaConsumerFactory) {
		return new MessageConsumerKafkaImpl(props.getBootstrapServers(), eventuateKafkaConsumerConfigurationProperties, kafkaConsumerFactory);
	}
}
