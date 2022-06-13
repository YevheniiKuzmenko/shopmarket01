package com.gmail.kuzmenk.yevhenii.shop.controllers;

import com.gmail.kuzmenk.yevhenii.shop.models.Product;
import com.gmail.kuzmenk.yevhenii.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("pattern") String pattern, Model model) {
        Product products = productService.findProductsByPattern(pattern);
        model.addAttribute("products", products);
        return "index";
    }







}
