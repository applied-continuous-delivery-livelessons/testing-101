package com.example.customerservice;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@SpringBootTest(classes = CustomerServiceApplication.class)
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveShouldReturnNewInstanceWithValidId() throws Exception {
        Customer save = customerRepository.save(
                new Customer("first", "last", "email@email.com"));
        Assert.assertThat(save.getId(), notNullValue());
    }

    @Test
    public void saveWhenEmailInvalidShouldThrowConstraintViolationException() throws Exception {
        expectedException.expect(ConstraintViolationException.class);
        customerRepository.save(new Customer("first", "last", null));
    }

    @Test
    public void findByIdAfterSaveShouldReturnAValidRecord() throws Exception {
        Customer savedRecord = customerRepository.save(new Customer("first", "last", "e@m.com"));
        Assert.assertThat(customerRepository.findOne(savedRecord.getId()), is(savedRecord));
    }
}
