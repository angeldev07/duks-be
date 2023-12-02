package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;



public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    Optional<Product> findById(Integer id);
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p where p.lowStock = true ")
    List<Product> findByLowStock();

    @Query("SELECT p FROM Product p where p.active = false ")
    List<Product> findByActive();

    @Query("SELECT p FROM Product p WHERE p.categories IS EMPTY")
    List<Product> findByNoCategory();
}
