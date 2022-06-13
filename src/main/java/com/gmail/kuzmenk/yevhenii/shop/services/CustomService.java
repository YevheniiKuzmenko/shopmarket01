package com.gmail.kuzmenk.yevhenii.shop.services;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import com.gmail.kuzmenk.yevhenii.shop.models.Customer.CustomerRole;
import com.gmail.kuzmenk.yevhenii.shop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer getUserByLogin(String login) {
        return customerRepository.findCustomerByLogin(login);
    }

    @Transactional
    public boolean existsByLogin(String login) {
        return customerRepository.existsByLogin(login);
    }

    @Transactional
    public void addCcustomer(String phoneNumber, String username, String password, String firstName, String lastName, String email) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String passHash = encoder.encode(password);
        Customer customer = new Customer(phoneNumber, username, passHash, firstName, lastName, email, CustomerRole.ADMIN);
        customerRepository.save(customer);
    }
    @Transactional
    public void editeCustomer(String firstName, String lastName,String email, String phoneNumber, Long customerId){
        Customer customer=customerRepository.getById(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customerRepository.save(customer);

    }



}
