package com.duk.dukscoffee.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Stock stock;

    private String name;

    private Double basePrice;

    private Integer amount;

    private boolean lowStock;

    private boolean active;

    private boolean sell;

    private boolean available;

    private boolean deleted;

    // Relation many to many with Category table.
    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties("products")
    private Set<Category> categories;

    // Relation many to many with orders table.
    @ManyToMany
    @JoinTable(
            name = "orders_x_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    @JsonIgnoreProperties("products")
    private Set<Order> orders;


}
