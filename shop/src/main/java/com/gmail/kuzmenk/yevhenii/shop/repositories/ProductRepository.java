package com.gmail.kuzmenk.yevhenii.shop.repositories;

import com.gmail.kuzmenk.yevhenii.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT c FROM Product c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    Product findProductByPattern(@Param("pattern") String pattern);


}
