package com.example.customerservice;

import junit.framework.AssertionFailedError;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
public class CustomerTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        LocalValidatorFactoryBean localValidatorFactoryBean =
                new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean.getValidator();
    }

    @Test
    public void newInstanceWithProperArguments() {
        Customer customer = new Customer(1L, "first", "last", "email@email.com");
        BDDAssertions.then(customer.getEmail()).isEqualTo("email@email.com");
        BDDAssertions.then(customer.getFirst()).isEqualTo("first");
        BDDAssertions.then(customer.getLast()).isEqualTo("last");
    }

    @Test
    public void newInstanceWithNullArgumentsShouldThrowException() throws IllegalArgumentException {
        Set<ConstraintViolation<Customer>> violations =
                validator.validate(new Customer(null, null, null));
        BDDAssertions.then(violations.size()).isEqualTo(3);
    }

}
