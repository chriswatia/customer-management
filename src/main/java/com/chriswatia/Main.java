package com.chriswatia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/customers")
public class Main {
	private final CustomerRepository _customerRepository;

	public Main(CustomerRepository customerRepository) {
		_customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@GetMapping("{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") Integer id){
		Customer customer = _customerRepository.findById(id).orElse(null);
		return customer;
	}
	@GetMapping
	public List<Customer> getCustomers(){
		return _customerRepository.findAll();
	}

	@PostMapping
	public Response addCustomer(@RequestBody NewCustomerRequest request){
		Customer customer = new Customer();
		customer.setName(request.name);
		customer.setEmail(request.email);
		customer.setAge(request.age);

		_customerRepository.save(customer);

		return new Response("Customer Created Successfully!");
	}

	@PutMapping("{customerId}")
	public Response updateCustomer(@PathVariable("customerId") Integer id,
								   @RequestBody NewCustomerRequest request){
		Customer customer = _customerRepository.findById(id).orElse(null);
		customer.setName(request.name);
		customer.setEmail(request.email);
		customer.setAge(request.age);

		_customerRepository.save(customer);
		return new Response("Customer updated successfully!");
	}

	@DeleteMapping("{customerId}")
	public Response deleteCustomer(@PathVariable("customerId") Integer id){
		_customerRepository.deleteById(id);

		return new Response("Customer deleted successfully!");
	}

	record NewCustomerRequest(
			String name,
			String email,
			Integer age
	){}

	record Response(
			String message
	) {
		@Override
		public boolean equals(Object obj) {
			return false;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public String toString() {
			return null;
		}
	}
}
