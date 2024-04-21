package com.duk.dukscoffee.http.DTO;
import java.util.Date;
import java.util.List;

import com.duk.dukscoffee.entities.Bill;
import com.duk.dukscoffee.entities.Client;
import com.duk.dukscoffee.entities.UserEntity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderXProductDetailsDTO {
    private Integer id;

    private UserDTO user;

    private Client client;

    private Date date;

    private List<ProductBillDTO> productList ;

    private Bill bill;
}
