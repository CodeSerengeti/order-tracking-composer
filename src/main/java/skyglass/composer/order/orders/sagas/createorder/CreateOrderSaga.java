package skyglass.composer.order.orders.sagas.createorder;

import static skyglass.composer.commands.consumer.CommandWithDestinationBuilder.send;

import skyglass.composer.commands.consumer.CommandWithDestination;
import skyglass.composer.commondomain.Money;
import skyglass.composer.order.customers.api.commands.ReserveCreditCommand;
import skyglass.composer.order.customers.api.replies.CustomerCreditLimitExceeded;
import skyglass.composer.order.customers.api.replies.CustomerNotFound;
import skyglass.composer.order.orders.common.RejectionReason;
import skyglass.composer.order.orders.domain.Order;
import skyglass.composer.order.orders.domain.OrderRepository;
import skyglass.composer.sagas.orchestration.SagaDefinition;
import skyglass.composer.sagas.simpledsl.SimpleSaga;

public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

	private OrderRepository orderRepository;

	public CreateOrderSaga(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	private SagaDefinition<CreateOrderSagaData> sagaDefinition = step()
			.invokeLocal(this::create)
			.withCompensation(this::reject)
			.step()
			.invokeParticipant(this::reserveCredit)
			.onReply(CustomerNotFound.class, this::handleCustomerNotFound)
			.onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded)
			.step()
			.invokeLocal(this::approve)
			.build();

	private void handleCustomerNotFound(CreateOrderSagaData data, CustomerNotFound reply) {
		data.setRejectionReason(RejectionReason.UNKNOWN_CUSTOMER);
	}

	private void handleCustomerCreditLimitExceeded(CreateOrderSagaData data, CustomerCreditLimitExceeded reply) {
		data.setRejectionReason(RejectionReason.INSUFFICIENT_CREDIT);
	}

	@Override
	public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
		return this.sagaDefinition;
	}

	private void create(CreateOrderSagaData data) {
		Order order = Order.createOrder(data.getOrderDetails());
		orderRepository.save(order);
		data.setOrderId(order.getId());
	}

	private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
		long orderId = data.getOrderId();
		Long customerId = data.getOrderDetails().getCustomerId();
		Money orderTotal = data.getOrderDetails().getOrderTotal();
		return send(new ReserveCreditCommand(customerId, orderId, orderTotal))
				.to("customerService")
				.build();
	}

	private void approve(CreateOrderSagaData data) {
		orderRepository.findById(data.getOrderId()).get().approve();
	}

	public void reject(CreateOrderSagaData data) {
		orderRepository.findById(data.getOrderId()).get().reject(data.getRejectionReason());
	}

}
