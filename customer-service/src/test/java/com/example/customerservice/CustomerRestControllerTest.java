package com.example.customerservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@SpringBootTest(classes = CustomerServiceApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CustomerRestControllerTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    private Customer c1 = new Customer(1L, "first", "last", "email@email.com");
    private Customer c2 = new Customer(2L, "first", "last", "email@email.com");


    @Test
    public void getCustomerByIdShouldReturnCustomer() throws Exception {

        Mockito
                .when(this.customerRepository.findOne(1L))
                .thenReturn(c1);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("@.id").value(1L));
    }

    @Test
    public void getCustomersShouldReturnAllCustomers() throws Exception {
        Mockito
                .when(this.customerRepository.findAll())
                .thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].first").value("first"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
