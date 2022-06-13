package com.gmail.kuzmenk.yevhenii.shop.controllers;

import com.gmail.kuzmenk.yevhenii.shop.services.EmailService;
import com.gmail.kuzmenk.yevhenii.shop.services.CustomService;
import com.gmail.kuzmenk.yevhenii.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @Autowired
    private CustomService customService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/productInOrder/add/{productId}")
    public String productNumberAdd(@PathVariable(value = "productId") Long productId, @RequestParam("quantity") int quantity, Model model) {
        orderService.addProductInOrder(productId, quantity);
        return "redirect:/";
    }

    @GetMapping("/productsInOrder/delete/{id}")
    public String productNumberDelete(@PathVariable(value = "id") Long id, Model model){
        orderService.deleteProductNumber(id);
        return "redirect:/home";

    }

    @GetMapping("/order")
    public String sendOrder(Model model){
        emailService.sendMessage();
        return "redirect:/";
    }



}
