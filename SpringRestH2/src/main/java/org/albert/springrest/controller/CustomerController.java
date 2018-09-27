package org.albert.springrest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.albert.springrest.model.Customer;
import org.albert.springrest.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		System.out.println("Get all Customers...");
		
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customers::add);
		
		return customers;
	}
	
	@PostMapping("/customers/create")
	public Customer postCustomer(@RequestBody Customer customer) {
		
		Customer _customer = customerRepository.save(new Customer(customer.getName(), customer.getAge()));
		return _customer;
		
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id){
		System.out.println("Delete Customer with ID = " + id + "...");
		
		customerRepository.deleteById(id);
		
		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}
	
	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers(){
		System.out.println("Delete All Customers...");
		
		customerRepository.deleteAll();
		
		return new ResponseEntity<>("All Customers have been deleted!", HttpStatus.OK);
	}
	
	@GetMapping("/customers/age/{age}")
	public List<Customer> findByAge(@PathVariable int age){
		
		List<Customer> customers = customerRepository.findByAge(age);
		return customers;
	}
	
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer){
		System.out.println("Update Customer with ID = " + id + "...");
		
		Optional<Customer> customerData = customerRepository.findById(id);
		
		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setName(customer.getName());
			_customer.setAge(customer.getAge());
			_customer.setActive(customer.isActive());
			return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
