package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.Product;
import java.util.Optional;



public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    Optional<Product> findById(Integer id);
    Optional<Product> findByName(String name);
}
