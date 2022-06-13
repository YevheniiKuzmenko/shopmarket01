package com.gmail.kuzmenk.yevhenii.shop.repositories;

import com.gmail.kuzmenk.yevhenii.shop.models.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT u from Customer u where u.login=:login")
    Customer findCustomerByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Customer u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);


}
