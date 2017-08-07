package com.example.customerclient;

import com.github.tomakehurst.wiremock.client.WireMock;
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

    private void configureCustomersResponse() {
        WireMock
                .stubFor(WireMock.get(WireMock.urlEqualTo("/customers"))
                        .willReturn(
                                WireMock
                                        .aResponse()
                                        .withBody("[{\"id\":1,\"first\":\"first\",\"last\":\"last\",\"email\":\"email\"}]")
                                        .withStatus(200)
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)));
    }

    @Test
    public void customerClientTest() throws Exception {

        configureCustomersResponse();
        Collection<Customer> customers = this.client.getCustomers();
        Assert.assertThat(customers, contains(
                new Customer(1L, "first", "last", "email")));
    }

}
