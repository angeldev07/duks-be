package com.duk.dukscoffee.services.interfaces;

import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.UserNotFoundException;
import com.duk.dukscoffee.http.DTO.OrderDTO;

public interface IOrderService {

    OrderDTO createOrder(OrderDTO orderDTO) throws UserNotFoundException, ClientNotFoundException;
    
}
