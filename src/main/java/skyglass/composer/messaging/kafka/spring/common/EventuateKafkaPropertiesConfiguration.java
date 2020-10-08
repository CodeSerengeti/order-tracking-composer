package skyglass.composer.messaging.kafka.spring.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.messaging.kafka.common.EventuateKafkaConfigurationProperties;

@Configuration
public class EventuateKafkaPropertiesConfiguration {

	@Bean
	public EventuateKafkaConfigurationProperties eventuateKafkaConfigurationProperties(@Value("${eventuatelocal.kafka.bootstrap.servers}") String bootstrapServers,
			@Value("${eventuatelocal.kafka.connection.validation.timeout:#{1000}}") long connectionValidationTimeout) {
		return new EventuateKafkaConfigurationProperties(bootstrapServers, connectionValidationTimeout);
	}
}
