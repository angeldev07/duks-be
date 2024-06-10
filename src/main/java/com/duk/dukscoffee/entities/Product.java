package com.duk.dukscoffee.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Stock stock;

    private String name;

    private Double basePrice;

    private Double discount;

    private Integer amount;

    private boolean lowStock;

    private boolean active;

    private boolean sell;

    private boolean available;

    private Integer iva;

    @Column(columnDefinition = "LONGTEXT")
    private String profileImg;

    // Relation many to many with Category table.
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    // Relation many to many with orders table.
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderXProduct> productOrderList;

    private boolean deleteFlag;
}
