package com.gmail.kuzmenk.yevhenii.shop.repositories;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import com.gmail.kuzmenk.yevhenii.shop.models.Product;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrder;
import com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder.ProductsInOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsInOrderRepository extends JpaRepository<ProductsInOrder, Long> {

    @Query("SELECT c FROM ProductsInOrder c WHERE c.customer = :customer")
    List<ProductsInOrder> findAllByCustomer(@Param("customer") Customer customer);

    @Query("SELECT c FROM ProductsInOrder c WHERE c.id = :productsInOrderKey")
    ProductsInOrder findProductsInOrderById(@Param("productsInOrderKey") ProductsInOrderKey productsInOrderKey);

    @Query("SELECT c FROM ProductsInOrder c WHERE c.product = :product and c.customer = :customer")
    ProductsInOrder findProductsInOrderByProduct(@Param("product") Product product, @Param("customer") Customer customer);

    @Query("SELECT c FROM ProductsInOrder c WHERE c.product=:product")
    List<ProductsInOrder> findAllByProduct(@Param("product") Product product);
}
