package com.duk.dukscoffee.http.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    

    private String name;

    private Double basePrice;

    private Integer amount;

    private boolean lowStock;

    private boolean active;

    private boolean sell;

    private boolean available;

    private boolean deleted;
}
