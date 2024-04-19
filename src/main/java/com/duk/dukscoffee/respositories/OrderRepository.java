package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
