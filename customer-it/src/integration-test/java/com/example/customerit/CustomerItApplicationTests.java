package com.example.customerit;

import com.example.customerclient.Customer;
import com.example.customerclient.CustomerClient;
import com.example.customerclient.CustomerClientConfiguration;
import org.assertj.core.api.BDDAssertions;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.applications.ApplicationManifestUtils;
import org.cloudfoundry.operations.applications.PushApplicationManifestRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CustomerItApplication.class,
        CustomerClientConfiguration.class,
        CloudFoundryClientConfiguration.class})
public class CustomerItApplicationTests {

    @Autowired
    private CloudFoundryOperations cf;

    @Autowired
    private CustomerClient client;

    private File manifest = new File("../manifest.yml");

    @Test
    public void clientCanTalkToService() {

        cf.applications()
                .pushManifest(
                        PushApplicationManifestRequest
                                .builder()
                                .manifest(ApplicationManifestUtils.read(this.manifest.toPath()).get(0))
                                .build())
                .block();

        Customer customerById = this.client.getCustomerById(1L);
        BDDAssertions.then(customerById.getId()).isEqualTo(1L);
        BDDAssertions.then(customerById.getFirstName()).isEqualTo("a");
        BDDAssertions.then(customerById.getLastName()).isEqualTo("a");
        BDDAssertions.then(customerById.getEmail()).isEqualTo("a@a.com");
    }

}
