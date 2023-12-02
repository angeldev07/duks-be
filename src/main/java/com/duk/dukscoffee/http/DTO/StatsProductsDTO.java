package com.duk.dukscoffee.http.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsProductsDTO {
    List<ProductDTO> productsWithouCategoriesDTOList;
    List<ProductDTO> productsLowStock;
    List<ProductDTO> productsDeactivate;
}
