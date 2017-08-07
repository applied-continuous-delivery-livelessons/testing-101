package com.example.customerclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@Configuration
public class CustomerClientConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CustomerClient customerClient() {
        return new CustomerClient(this.restTemplate());
    }
}
