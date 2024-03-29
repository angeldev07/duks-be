package com.duk.dukscoffee.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cardId;

    private char gender;

    private Date birthDay;

    private boolean active;

    private Date lastVisit;

    private String address;

    private String phone;

    private boolean deleteFlag;

    // Relation one to many  with order table.
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Order> orders;
}
