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


    private String name;

    private Double basePrice;

    private Integer amount;

}
