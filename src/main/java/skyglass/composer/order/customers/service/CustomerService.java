package skyglass.composer.order.customers.service;

import javax.transaction.Transactional;

import skyglass.composer.commondomain.Money;
import skyglass.composer.order.customers.domain.Customer;
import skyglass.composer.order.customers.domain.CustomerCreditLimitExceededException;
import skyglass.composer.order.customers.domain.CustomerNotFoundException;
import skyglass.composer.order.customers.domain.CustomerRepository;

public class CustomerService {

	private CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Transactional
	public Customer createCustomer(String name, Money creditLimit) {
		Customer customer = new Customer(name, creditLimit);
		return customerRepository.save(customer);
	}

	public void reserveCredit(long customerId, long orderId, Money orderTotal) throws CustomerCreditLimitExceededException {
		Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		customer.reserveCredit(orderId, orderTotal);
	}
}
