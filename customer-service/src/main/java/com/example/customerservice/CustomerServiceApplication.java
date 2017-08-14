package com.example.customerservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    @Bean
    ApplicationRunner applicationRunner(CustomerRepository customerRepository) {
        return args -> {

            Stream.of("a", "b", "c")
                    .forEach(n -> customerRepository.save(new Customer(null, n, n,
                    n + "@" + n + ".com")));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
