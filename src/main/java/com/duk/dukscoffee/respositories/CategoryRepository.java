package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.Category;
import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    Optional<Category> findByName(String name);
}
