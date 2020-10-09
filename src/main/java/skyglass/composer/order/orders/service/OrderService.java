package skyglass.composer.order.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import skyglass.composer.order.orders.common.OrderDetails;
import skyglass.composer.order.orders.domain.Order;
import skyglass.composer.order.orders.domain.OrderRepository;
import skyglass.composer.order.orders.sagas.createorder.CreateOrderSaga;
import skyglass.composer.order.orders.sagas.createorder.CreateOrderSagaData;
import skyglass.composer.sagas.orchestration.SagaInstanceFactory;

public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SagaInstanceFactory sagaInstanceFactory;

	@Autowired
	private CreateOrderSaga createOrderSaga;

	public OrderService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
		this.orderRepository = orderRepository;
		this.sagaInstanceFactory = sagaInstanceFactory;
		this.createOrderSaga = createOrderSaga;
	}

	@Transactional
	public Order createOrder(OrderDetails orderDetails) {
		CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
		sagaInstanceFactory.create(createOrderSaga, data);
		return orderRepository.findById(data.getOrderId()).get();
	}

}
