package com.gmail.kuzmenk.yevhenii.shop.controllers;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrder;
import com.gmail.kuzmenk.yevhenii.shop.services.CustomService;
import com.gmail.kuzmenk.yevhenii.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private CustomService customService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        return "registration";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customService.getUserByLogin(user.getUsername());
        List<ProductsInOrder> productsInOrderList = orderService.findProductsInOrderByCustomer(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("productsInOrderList", productsInOrderList);
        model.addAttribute("totalWeight", orderService.getTotalWeight(productsInOrderList));
        model.addAttribute("totalPrise", orderService.getTotalPrise(productsInOrderList));
        return "home";
    }

    @PostMapping("/registration")
    public String createNewUser(@RequestParam String username,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String phoneNumber,
                                @RequestParam String password, Model model) {
        if (customService.existsByLogin(username)) {
            model.addAttribute("exists", true);
            return "registration";
        }
        customService.addCcustomer(phoneNumber, username, password, firstName, lastName, email);
        return "login";
    }

    @PostMapping("/customer/edit/{id}")
    public String editeCustomer(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String phoneNumber,
                                @PathVariable(value = "id") Long customerId, Model model) {
        customService.editeCustomer(firstName, lastName, email, phoneNumber, customerId);
        return "redirect:/home";
    }


}
