package ru.gb.springbootmarket.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.springbootmarket.model.Customer;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c JOIN FETCH c.orderList")
    List<Customer> getAll();
}
