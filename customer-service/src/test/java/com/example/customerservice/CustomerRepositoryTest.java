package com.example.customerservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

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
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByIdAfterSaveShouldReturnAValidRecord() throws Exception {
        Customer entity = new Customer("first", "last", "e@m.com");
        Customer save = this.testEntityManager.persistFlushFind(entity);
        BDDAssertions.then(save.getFirst()).isEqualTo(entity.getFirst());
        BDDAssertions.then(save.getLast()).isEqualTo(entity.getLast());
        BDDAssertions.then(save.getEmail()).isEqualTo(entity.getEmail());
        BDDAssertions.then(save.getId()).isNotNull();
    }

    @Test
    public void saveShouldReturnNewInstanceWithValidId() throws Exception {
        Customer entity = new Customer("first", "last", "email@email.com");
        Customer save = customerRepository.save(entity);
        BDDAssertions.then(save.getFirst()).isEqualTo(entity.getFirst());
        BDDAssertions.then(save.getLast()).isEqualTo(entity.getLast());
        BDDAssertions.then(save.getEmail()).isEqualTo(entity.getEmail());
        BDDAssertions.then(save.getId()).isNotNull();
    }

    @Test
    public void saveWhenEmailInvalidShouldThrowConstraintViolationException() throws Exception {
        expectedException.expect(ConstraintViolationException.class);
        customerRepository.save(new Customer("first", "last", null));
    }
}
