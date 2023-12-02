package com.duk.dukscoffee.http.DTO;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    
    private Integer id;

    private String name;

    private String lastName;

    private String email;

    private String cardId;

    private char gender;

    private Date birthDay;

    private boolean active;

    private Date lastVisit;

    private String address;

    private String phone;
}
