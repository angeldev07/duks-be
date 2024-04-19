package com.duk.dukscoffee.http.DTO;


import com.duk.dukscoffee.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String profileImg;

    private Integer id;

    private String name;

    private Double discount;
    
    private Double basePrice;

    private Integer amount;

    private boolean active;

    private CategoryDTO category;

}
