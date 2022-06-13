package com.gmail.kuzmenk.yevhenii.shop.controllers;

import com.gmail.kuzmenk.yevhenii.shop.models.Product;
import com.gmail.kuzmenk.yevhenii.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/add")
    public String productAdd(@RequestParam("photo")MultipartFile photo,
                             @RequestParam String name,
                             @RequestParam String specification,
                             @RequestParam double weight,
                             @RequestParam double price){
        productService.addProduct(photo, name, specification, weight, price);
        return "redirect:/product/add";
    }

    @GetMapping("/product/add")
    public String productAddPage(Model model){
        return "product_add";
    }

    @GetMapping("/product/delete/{id}")
    public String productDelete(@PathVariable(value = "id") long id, Model model) {
        productService.productDelete(id);
        return "redirect:/";
    }

    @GetMapping("/product/edit/{id}")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productEdit";
    }

    @PostMapping("/product/edit/{id}")
    public String productUpdate(@PathVariable(value = "id") long id,
                                @RequestParam("newPhoto") MultipartFile newPhoto,
                                @RequestParam String specification,
                                @RequestParam String name,
                                @RequestParam double weight,
                                @RequestParam double price, Model model) throws IOException {
        productService.productEdit(id, newPhoto, specification, name, weight, price);
        return "redirect:/";
    }

    @GetMapping("/product/details/{id}")
    public String productDetails(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }

}
