package com.duk.dukscoffee.http.DTO;



import java.util.Date;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDTO {
    
    private Integer id;

    private Double basePrice;

    private boolean iva;

    private Double totalPrice;

    private Double discounts;

    private Date dateBill;

    private Integer orderId;
}
