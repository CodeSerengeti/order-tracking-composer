package skyglass.composer.order.customers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import skyglass.composer.order.customers.domain.Customer;
import skyglass.composer.order.customers.domain.CustomerRepository;
import skyglass.composer.order.customers.service.CustomerService;
import skyglass.composer.order.customers.webapi.CreateCustomerRequest;
import skyglass.composer.order.customers.webapi.CreateCustomerResponse;
import skyglass.composer.order.customers.webapi.GetCustomerResponse;

@RestController
public class CustomerController {

	private CustomerService customerService;

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
		this.customerService = customerService;
		this.customerRepository = customerRepository;
	}

	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
		Customer customer = customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCreditLimit());
		return new CreateCustomerResponse(customer.getId());
	}

	@RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<GetCustomerResponse> getCustomer(@PathVariable Long customerId) {
		return customerRepository
				.findById(customerId)
				.map(c -> new ResponseEntity<>(new GetCustomerResponse(c.getId(), c.getName(), c.getCreditLimit()), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
