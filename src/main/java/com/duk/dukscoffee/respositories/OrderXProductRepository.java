package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.OrderXProduct;

public interface OrderXProductRepository extends JpaRepository<OrderXProduct, Integer>{
    
}
