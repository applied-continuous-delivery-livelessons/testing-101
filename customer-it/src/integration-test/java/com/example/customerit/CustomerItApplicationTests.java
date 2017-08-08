package com.example.customerit;

import com.example.customerclient.Customer;
import com.example.customerclient.CustomerClient;
import com.example.customerclient.CustomerClientConfiguration;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.operations.applications.ApplicationManifestUtils;
import org.cloudfoundry.operations.applications.PushApplicationManifestRequest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * <blockquote>
 * An integration test verifies the communication paths and interactions between
 * components to detect interface defects. -Martin Fowler
 * </blockquote>
 *
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerItApplicationTests.Config.class)
public class CustomerItApplicationTests {

    @Import(CustomerClientConfiguration.class)
    @SpringBootApplication
    public static class Config {
    }

    private File file = new File("../manifest.yml");

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private DefaultCloudFoundryOperations cloudFoundryOperations;

    @Before
    public void before() {

        this.cloudFoundryOperations
                .applications()
                .pushManifest(
                        PushApplicationManifestRequest
                                .builder()
                                .manifest(ApplicationManifestUtils.read(this.file.toPath()).get(0))
                                .build())
                .block();
    }

    @Test
    public void getCustomerById() {
        Customer byId = this.customerClient.getCustomerById(1L);
        Assert.assertThat(byId.getFirst(), Matchers.is("a"));
        Assert.assertThat(byId.getLast(), Matchers.is("a"));
        Assert.assertThat(byId.getEmail(), Matchers.is("a@a.com"));
    }

}
