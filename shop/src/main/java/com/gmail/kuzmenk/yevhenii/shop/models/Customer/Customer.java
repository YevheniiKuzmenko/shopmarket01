package com.gmail.kuzmenk.yevhenii.shop.models.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private CustomerRole role;

    public Customer(String phoneNumber, String login, String password, String firstName, String lastName, String email, CustomerRole role) {
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }



}
