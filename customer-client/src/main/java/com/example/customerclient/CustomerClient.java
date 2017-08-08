package com.example.customerclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */

public class CustomerClient {

    private final RestTemplate restTemplate;
    private final String host;

    public CustomerClient(RestTemplate rt, String csHost) {
        this.restTemplate = rt;
        this.host = csHost;
    }

    public Collection<Customer> getCustomers() {

        ParameterizedTypeReference<Collection<Customer>> responseType
                = new ParameterizedTypeReference<Collection<Customer>>() {
        };

        return restTemplate.exchange(
                host + "/customers",
                HttpMethod.GET,
                null,
                responseType)
                .getBody();
    }

    public Customer getCustomerById(Long id) {
        return this.restTemplate
                .exchange(host + "/customers/{id}", HttpMethod.GET, null, Customer.class, id)
                .getBody();
    }
}
