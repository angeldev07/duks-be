package com.duk.dukscoffee.services.interfaces;

import java.util.List;

import com.duk.dukscoffee.entities.Order;
import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.UserNotFoundException;
import com.duk.dukscoffee.http.DTO.OrderDTO;

public interface IOrderService {

    OrderDTO createOrder(OrderDTO orderDTO) throws UserNotFoundException, ClientNotFoundException;
    
    List<Order> getOrders();
}
