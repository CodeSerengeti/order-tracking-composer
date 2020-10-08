package skyglass.composer.order.customers.domain;

import java.util.Collections;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import skyglass.composer.commondomain.Money;

@Entity
@Table(name = "Customer")
@Access(AccessType.FIELD)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Embedded
	private Money creditLimit;

	@ElementCollection
	private Map<Long, Money> creditReservations;

	@Version
	private Long version;

	Money availableCredit() {
		return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
	}

	public Customer() {
	}

	public Customer(String name, Money creditLimit) {
		this.name = name;
		this.creditLimit = creditLimit;
		this.creditReservations = Collections.emptyMap();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Money getCreditLimit() {
		return creditLimit;
	}

	public void reserveCredit(Long orderId, Money orderTotal) {
		if (availableCredit().isGreaterThanOrEqual(orderTotal)) {
			creditReservations.put(orderId, orderTotal);
		} else
			throw new CustomerCreditLimitExceededException();
	}
}
