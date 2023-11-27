package com.duk.dukscoffee.http.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoryDTO {
    private Integer id;

    private String name;
    
    private Boolean active;
    
}
