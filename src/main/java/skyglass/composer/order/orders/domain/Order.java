package skyglass.composer.order.orders.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import skyglass.composer.order.orders.common.OrderDetails;
import skyglass.composer.order.orders.common.OrderState;
import skyglass.composer.order.orders.common.RejectionReason;

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderState state;

	@Embedded
	private OrderDetails orderDetails;

	@Enumerated(EnumType.STRING)
	private RejectionReason rejectionReason;

	@Version
	private Long version;

	public Order() {
	}

	public Order(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
		this.state = OrderState.PENDING;
	}

	public static Order createOrder(OrderDetails orderDetails) {
		return new Order(orderDetails);
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void approve() {
		this.state = OrderState.APPROVED;
	}

	public void reject(RejectionReason rejectionReason) {
		this.state = OrderState.REJECTED;
		this.rejectionReason = rejectionReason;
	}

	public OrderState getState() {
		return state;
	}

	public RejectionReason getRejectionReason() {
		return rejectionReason;
	}
}
