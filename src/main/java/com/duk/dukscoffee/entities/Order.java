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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    // Relation many to one with user table.
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    // Relation many to one  with client table.
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    // relation many to one with bill table
    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    private Bill bill;

    // Relation many to many with products table.
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderXProduct> productoOrdenList;

    private Date date;

}
