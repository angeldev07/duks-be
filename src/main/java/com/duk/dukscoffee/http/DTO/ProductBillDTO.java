package com.duk.dukscoffee.http.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductBillDTO extends ProductDTO{

    private Integer amountBill ;
}
