package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duk.dukscoffee.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{
    
}
