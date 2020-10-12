package skyglass.composer.order.integrationtests;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import skyglass.composer.order.customers.CustomerConfiguration;
import skyglass.composer.order.orders.OrderConfiguration;
import skyglass.composer.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import skyglass.composer.spring.events.publisher.TramEventsPublisherConfiguration;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@Import({
		OrderConfiguration.class,
		CustomerConfiguration.class,
		TramEventsPublisherConfiguration.class,
		SagaOrchestratorConfiguration.class

})
public class OrdersAndCustomersIntegrationCommonIntegrationTestConfiguration {

	@Bean
	public TramCommandsAndEventsIntegrationData tramCommandsAndEventsIntegrationData() {
		return new TramCommandsAndEventsIntegrationData();
	}

}
