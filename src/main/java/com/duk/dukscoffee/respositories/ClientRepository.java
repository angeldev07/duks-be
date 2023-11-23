package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.duk.dukscoffee.entities.Client;
import java.util.Optional;



public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByCardId(String cardId);

    Optional<Client> findById(Integer id);

    @Query("SELECT c FROM Client c WHERE c.cardId= ?1 or c.email = ?2")
    Optional<Client> findByCardIdOrEmail(String cardId, String email);
} 
