package com.example.customerservice;

import org.springframework.http.MediaType;
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

    @GetMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer customerById(@PathVariable Long id) {
        return this.customerRepository.findOne(id);
    }

    @GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Customer> customers() {
        return this.customerRepository.findAll();
    }
}
