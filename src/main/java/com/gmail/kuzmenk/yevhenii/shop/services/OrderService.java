package com.gmail.kuzmenk.yevhenii.shop.services;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import com.gmail.kuzmenk.yevhenii.shop.models.Product;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrder;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrderKey;
import com.gmail.kuzmenk.yevhenii.shop.repositories.CustomerRepository;
import com.gmail.kuzmenk.yevhenii.shop.repositories.ProductRepository;
import com.gmail.kuzmenk.yevhenii.shop.repositories.ProductsInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductsInOrderRepository productsInOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void addProductInOrder(Long productId, int quantity){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerRepository.findCustomerByLogin(user.getUsername());
        Product product= productRepository.getById(productId);
        ProductsInOrder productsInOrderNew=new ProductsInOrder(customer,product,quantity);
        List<ProductsInOrder> productsInOrderList=productsInOrderRepository.findAllByCustomer(customer);
        for (ProductsInOrder productsInOrder : productsInOrderList) {
            if (productId.equals(productsInOrder.getProduct().getId())){
                productsInOrderNew=productsInOrderRepository.findProductsInOrderById(productsInOrder.getId());
                productsInOrder.setQuantity(productsInOrder.getQuantity()+quantity);
                break;
            }
        }
        productsInOrderRepository.save(productsInOrderNew);
    }
    @Transactional
    public List<ProductsInOrder> findProductsInOrderByCustomer(Customer customer){
        return productsInOrderRepository.findAllByCustomer(customer);
    }

    @Transactional
    public double getTotalWeight(List<ProductsInOrder> productsInOrderList) {
        double totalWeight = 0;
        for (ProductsInOrder productsInOrder : productsInOrderList) {
            totalWeight = totalWeight + productsInOrder.getProduct().getWeight() * productsInOrder.getQuantity();
        }
        return totalWeight;
    }

    @Transactional
    public double getTotalPrise(List<ProductsInOrder> productsInOrderList) {
        double totalPrise = 0;
        for (ProductsInOrder productsInOrder : productsInOrderList) {
            totalPrise = totalPrise + productsInOrder.getProduct().getPrice() * productsInOrder.getQuantity();

        }
        return totalPrise;
    }

    @Transactional
    public void deleteProductNumber(Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerRepository.findCustomerByLogin(user.getUsername());
        Product product = productRepository.getById(id);
        productsInOrderRepository.delete(productsInOrderRepository.findProductsInOrderByProduct(product, customer));
    }

}
