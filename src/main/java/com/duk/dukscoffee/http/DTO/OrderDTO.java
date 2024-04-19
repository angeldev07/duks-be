package com.duk.dukscoffee.http.DTO;

import java.util.Date;
import java.util.List;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Integer id;

    private Integer userId;

    private Integer clientId;

    private Date date;

    private List<OrderXProductDTO> orderxproducts;

    private Integer billId;
    
}
