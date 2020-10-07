package skyglass.composer.ordersandcustomers.commondomain;

public class OrderCreatedEvent implements OrderEvent {

	private OrderDetails orderDetails;

	public OrderCreatedEvent() {
	}

	public OrderCreatedEvent(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
}
