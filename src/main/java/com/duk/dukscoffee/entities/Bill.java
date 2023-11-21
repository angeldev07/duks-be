package com.duk.dukscoffee.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private Double basePrice;

    private boolean iva;

    private Double totalPrice;

    private Double discounts;

    private Date bill;

    @OneToMany(mappedBy =  "bill")
    @JsonManagedReference
    private List<Order> order;


}
