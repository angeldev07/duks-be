package com.duk.dukscoffee.http.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderXProductDTO {
    
    private Integer id;

    private Integer productId;

    private Integer amount;
}
