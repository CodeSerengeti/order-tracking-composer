package skyglass.composer.order;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import skyglass.composer.order.customers.CustomerConfiguration;
import skyglass.composer.order.web.CustomerWebConfiguration;
import skyglass.composer.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;

@Configuration
@EnableAutoConfiguration
@Import({ CustomerConfiguration.class,
		CustomerWebConfiguration.class,
		TramMessageProducerJdbcConfiguration.class,
		EventuateTramKafkaMessageConsumerConfiguration.class })
@ComponentScan
public class OrderTrackingConfiguration {

}
