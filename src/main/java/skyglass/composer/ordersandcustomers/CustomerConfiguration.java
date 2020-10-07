package skyglass.composer.ordersandcustomers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import skyglass.composer.commands.consumer.CommandDispatcher;
import skyglass.composer.ordersandcustomers.domain.CustomerRepository;
import skyglass.composer.ordersandcustomers.service.CustomerCommandHandler;
import skyglass.composer.ordersandcustomers.service.CustomerService;
import skyglass.composer.sagas.participant.SagaCommandDispatcherFactory;
import skyglass.composer.sagas.spring.participant.SagaParticipantConfiguration;

@Configuration
@Import(SagaParticipantConfiguration.class)
@EnableJpaRepositories
@EnableAutoConfiguration
public class CustomerConfiguration {

	@Bean
	public CustomerService customerService(CustomerRepository customerRepository) {
		return new CustomerService(customerRepository);
	}

	@Bean
	public CustomerCommandHandler customerCommandHandler(CustomerService customerService) {
		return new CustomerCommandHandler(customerService);
	}

	// TODO Exception handler for CustomerCreditLimitExceededException

	@Bean
	public CommandDispatcher consumerCommandDispatcher(CustomerCommandHandler target,
			SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

		return sagaCommandDispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
	}

}
