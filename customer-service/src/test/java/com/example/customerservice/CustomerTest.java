package com.example.customerservice;

import org.hamcrest.Matchers;
import org.junit.Assert;
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
    public void newInstanceWithNullArgumentsShouldThrowException() throws IllegalArgumentException {
        Set<ConstraintViolation<Customer>> violations =
                validator.validate(new Customer(null, null, null));
        Assert.assertThat(violations.size(), Matchers.equalTo(3));
    }

}
