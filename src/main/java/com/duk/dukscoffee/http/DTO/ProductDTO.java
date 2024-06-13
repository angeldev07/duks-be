package com.duk.dukscoffee.http.DTO;


import com.duk.dukscoffee.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String profileImg;

    private Integer id;

    private String name;
    
    private Double basePrice;

    private Integer amount;

    private boolean active;

    private Integer iva;

    private Integer stock;

    private CategoryDTO category;

}
