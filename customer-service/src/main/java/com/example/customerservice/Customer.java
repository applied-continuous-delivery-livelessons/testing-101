package com.example.customerservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="josh@joshlong.com">Josh Long</a>
 */
@Data
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String first, last;

    @Email
    @NotNull
    private String email;

    public Customer(Long id, String f, String l, String e) {
        this(f, l, e);
        this.id = id;
    }

    public Customer(String first, String last, String email) {
        this.email = email;
        this.first = first;
        this.last = last;
    }
}
