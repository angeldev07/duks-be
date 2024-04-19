package com.duk.dukscoffee.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.UserNotFoundException;
import com.duk.dukscoffee.http.DTO.AllOrdersDTO;
import com.duk.dukscoffee.http.DTO.OrderDTO;
import com.duk.dukscoffee.services.implementations.OrderService;
import com.duk.dukscoffee.services.interfaces.IOrderService;

import com.duk.dukscoffee.http.response.HttpResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createOrder(@RequestBody OrderDTO orderDTO) throws UserNotFoundException, ClientNotFoundException {
        orderService.createOrder(orderDTO);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase(),
                        "Order created successfully"),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AllOrdersDTO>> getOrders() {
        return ResponseEntity.ok(
                orderService.getOrders().stream().map(order -> {
                    AllOrdersDTO orderDTO = new AllOrdersDTO();
                    BeanUtils.copyProperties(order, orderDTO);
                    return orderDTO;
                }).collect(Collectors.toList())
        );
    }

    
}
