package skyglass.composer.ordersandcustomers.service;

import skyglass.composer.ordersandcustomers.commondomain.Money;
import skyglass.composer.ordersandcustomers.domain.Customer;
import skyglass.composer.ordersandcustomers.domain.CustomerCreditLimitExceededException;
import skyglass.composer.ordersandcustomers.domain.CustomerNotFoundException;
import skyglass.composer.ordersandcustomers.domain.CustomerRepository;

public class CustomerService {

	private CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer createCustomer(String name, Money creditLimit) {
		Customer customer = new Customer(name, creditLimit);
		return customerRepository.save(customer);
	}

	public void reserveCredit(long customerId, long orderId, Money orderTotal) throws CustomerCreditLimitExceededException {
		Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		customer.reserveCredit(orderId, orderTotal);
	}
}
