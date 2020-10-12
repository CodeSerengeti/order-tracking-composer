package skyglass.composer.order.integrationtests;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import skyglass.composer.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;

@Configuration
@Import({ OrdersAndCustomersIntegrationCommonIntegrationTestConfiguration.class,
		TramMessageProducerJdbcConfiguration.class,
		EventuateTramKafkaMessageConsumerConfiguration.class })
public class OrdersAndCustomersIntegrationTestConfiguration {
}
