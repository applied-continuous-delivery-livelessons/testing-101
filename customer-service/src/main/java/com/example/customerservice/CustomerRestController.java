package com.example.customerservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@RestController
public class CustomerRestController {

    private final CustomerRepository customerRepository;

    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    Collection<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    Customer getCustomerById(@PathVariable Long id) {
        return customerRepository.findOne(id);
    }
}
