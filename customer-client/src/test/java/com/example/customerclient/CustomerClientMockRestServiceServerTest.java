package com.example.customerclient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
public class CustomerClientMockRestServiceServerTest {

    private MockRestServiceServer mockRestServiceServer;
    private ClassPathResource customerByIdJson = new ClassPathResource("customer-by-id.json");
    private ClassPathResource customersJson = new ClassPathResource("customers.json");

    @Autowired
    private CustomerClient client;

    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void before() {
        this.mockRestServiceServer = MockRestServiceServer
                .bindTo(this.restTemplate)
                .build();
    }

    @Test
    public void getCustomers() throws Exception {
        this.mockRestServiceServer
                .expect(manyTimes(), requestTo("http://localhost:8080/customers"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customersJson, MediaType.APPLICATION_JSON_UTF8));
        Collection<Customer> customers = this.client.getCustomers();
        Assert.assertThat(customers, contains(
                new Customer(1L, "first", "last", "email")));
        this.mockRestServiceServer.verify();
    }

    @Test
    public void getCustomerById() {
        this.mockRestServiceServer
                .expect(manyTimes(), requestTo("http://localhost:8080/customers/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customerByIdJson, MediaType.APPLICATION_JSON_UTF8));
        Customer customerById = this.client.getCustomerById(1L);
        Assert.assertThat(customerById, org.hamcrest.Matchers.notNullValue());
        this.mockRestServiceServer.verify();
    }
}
