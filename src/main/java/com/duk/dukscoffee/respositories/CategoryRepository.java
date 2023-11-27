package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c from Category c where c.deleteFlag = false")
    List<Category> findAll();

    Optional<Category> findByName(String name);
}
