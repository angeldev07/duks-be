package com.duk.dukscoffee.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.duk.dukscoffee.entities.Client;

import java.util.Date;
import java.util.List;
import java.util.Optional;



public interface ClientRepository extends JpaRepository<Client, Integer> {

        @Query("SELECT c FROM Client c WHERE c.deleteFlag = false")
    List<Client> findAll();

    Optional<Client> findByCardId(String cardId);

    Optional<Client> findById(Integer id);

    @Query("SELECT c FROM Client c WHERE c.cardId= ?1 or c.email = ?2")
    Optional<Client> findByCardIdOrEmail(String cardId, String email);

    Optional<Client> findByEmail(String email);

    List<Client> findByName(String name);

    List<Client> findByLastName(String lastName);

    List<Client> findByBirthDay(Date birthDay);

    List<Client> findByLastVisit(Date lastVisit);
} 
