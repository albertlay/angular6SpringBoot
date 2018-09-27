package org.albert.springrest.repo;

import java.util.List;

import org.albert.springrest.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	List<Customer> findByAge(int age);

}
