package com.example.customerclient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.contains;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8080)
public class CustomerClientTest {

    @Autowired
    private CustomerClient client;

    private void configureDefaultCustomersResponse() {
        stubFor(get(urlEqualTo("/customers"))
                        .willReturn(
                                aResponse()
                                        .withBody("[{\"id\":1,\"first\":\"first\",\"last\":\"last\",\"email\":\"email\"}]")
                                        .withStatus(200)
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)));
    }

    @Test
    public void getCustomers() throws Exception {
        configureDefaultCustomersResponse();
        Collection<Customer> customers = this.client.getCustomers();
        Assert.assertThat(customers, contains(
                new Customer(1L, "first", "last", "email")));
    }

    @Test
    public void getCustomersById() {
        configureDefaultCustomersByIdResponse();
        Customer customerById = this.client.getCustomerById(1L);
        Assert.assertThat(customerById, org.hamcrest.Matchers.notNullValue());
    }

    private void configureDefaultCustomersByIdResponse() {
        stubFor(get(urlEqualTo("/customers/1"))
                .willReturn(aResponse()
                        .withBody(" {\"id\":1,\"first\":\"first\",\"last\":\"last\",\"email\":\"email\"} ")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)));
    }
}
