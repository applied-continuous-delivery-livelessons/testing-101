package com.example.customerclient;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock
public class CustomerClientWireMockTest {

    private ClassPathResource customerByIdJson = new ClassPathResource("customer-by-id.json");
    private ClassPathResource customersJson = new ClassPathResource("customers.json");

    @Autowired
    private CustomerClient client;

    @Test
    public void getCustomers() throws Exception {
        stubFor(get(urlEqualTo("/customers"))
                .willReturn(
                        aResponse()
                                .withBody(responseAsString(customersJson))
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)));
        Collection<Customer> customers = this.client.getCustomers();
        BDDAssertions.then(customers).contains(new Customer(1L, "first", "last", "email"));
    }

    @Test
    public void getCustomersById() {
        stubFor(get(urlEqualTo("/customers/1"))
                .willReturn(aResponse()
                        .withBody(responseAsString(customerByIdJson))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)));
        Customer customerById = this.client.getCustomerById(1L);
        BDDAssertions.then(customerById).isNotNull();
    }

    private String responseAsString(ClassPathResource customersJson) {
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(customersJson.getInputStream()))) {
            return buffer.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
