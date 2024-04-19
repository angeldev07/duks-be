package com.duk.dukscoffee.http.DTO;

import java.util.Date;
import java.util.List;

import com.duk.dukscoffee.entities.Bill;
import com.duk.dukscoffee.entities.Client;
import com.duk.dukscoffee.entities.OrderXProduct;
import com.duk.dukscoffee.entities.UserEntity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDTO {
    
    private Integer id;

    private UserEntity user;

    private Client client;

    private Date date;

    private List<OrderXProduct> productoOrdenList ;

    private Bill bill;
}
