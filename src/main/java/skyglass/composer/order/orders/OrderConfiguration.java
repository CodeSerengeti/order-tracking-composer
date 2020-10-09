package skyglass.composer.order.orders;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import skyglass.composer.order.orders.domain.OrderRepository;
import skyglass.composer.order.orders.sagas.createorder.CreateOrderSaga;
import skyglass.composer.order.orders.service.OrderService;
import skyglass.composer.sagas.orchestration.SagaInstanceFactory;
import skyglass.composer.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import skyglass.composer.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({ SagaOrchestratorConfiguration.class, OptimisticLockingDecoratorConfiguration.class })
public class OrderConfiguration {

	@Bean
	public OrderService orderService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
		return new OrderService(orderRepository, sagaInstanceFactory, createOrderSaga);
	}

	@Bean
	public CreateOrderSaga createOrderSaga(OrderRepository orderRepository) {
		return new CreateOrderSaga(orderRepository);
	}

}
