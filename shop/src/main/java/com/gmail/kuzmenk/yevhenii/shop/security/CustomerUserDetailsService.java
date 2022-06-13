package com.gmail.kuzmenk.yevhenii.shop.security;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import com.gmail.kuzmenk.yevhenii.shop.services.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    CustomService customService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        Customer customer=customService.getUserByLogin(login);
        if (customer == null)
            throw new UsernameNotFoundException(login + " not found");

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(customer.getRole().toString()));

        return new User(customer.getLogin(), customer.getPassword(), roles);
    }
}
