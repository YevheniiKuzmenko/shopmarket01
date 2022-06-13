package com.gmail.kuzmenk.yevhenii.shop.services;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrder;
import com.gmail.kuzmenk.yevhenii.shop.repositories.CustomerRepository;
import com.gmail.kuzmenk.yevhenii.shop.repositories.ProductsInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductsInOrderRepository productsInOrderRepository;

    public void sendMessage() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerRepository.findCustomerByLogin(user.getUsername());
        List<ProductsInOrder> productsInOrderList = productsInOrderRepository.findAllByCustomer(customer);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yevheniikuzmenko2@gmail.com");
        message.setTo("y.k.230990@gmail.com");
        String text = customer.getLastName()+" "+customer.getFirstName()+" "+customer.getPhoneNumber();
//        text.contains(customer.getLastName()+" "+customer.getFirstName()+" "+customer.getPhoneNumber()+System.lineSeparator());
        for (ProductsInOrder productsInOrder : productsInOrderList) {
//            text.contains(productsInOrder.getProduct().getName()+" количестго-"+productsInOrder.getQuantity()+" цена-"+productsInOrder.getProduct().getPrice()+System.lineSeparator());
            text=text+ System.lineSeparator()+productsInOrder.getProduct().getName()+" количестго-"+productsInOrder.getQuantity()+" цена на сайте-"+productsInOrder.getProduct().getPrice();
        }
//        message.setText(customer.getLastName()+" "+customer.getFirstName()+" "+customer.getPhoneNumber());
        message.setText(text);
        emailSender.send(message);
        productsInOrderRepository.deleteAll(productsInOrderList);

    }
}
