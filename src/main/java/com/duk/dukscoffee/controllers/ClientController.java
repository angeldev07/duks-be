package com.duk.dukscoffee.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.MethodNotAllowedException;

import com.duk.dukscoffee.exceptions.CardIdExistException;
import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.EmailExistException;
import com.duk.dukscoffee.exceptions.ExceptionHandling;
import com.duk.dukscoffee.http.DTO.ClientDTO;
import com.duk.dukscoffee.http.response.HttpResponse;
import com.duk.dukscoffee.services.implementations.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController extends ExceptionHandling {
    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createClient(@RequestBody ClientDTO clientDTO) throws EmailExistException, CardIdExistException {
        clientService.createClient(clientDTO);

        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase(), "Client created successfully"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClientInfoDetails(@PathVariable Integer clientId) throws ClientNotFoundException {
        ClientDTO client = new ClientDTO();
        BeanUtils.copyProperties(clientService.getClientInfoDetails(clientId), client);

        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients() {
        return ResponseEntity.ok(
                clientService.getClients().stream().map(client -> {
                    ClientDTO clientDTO = new ClientDTO();
                    BeanUtils.copyProperties(client, clientDTO);
                    return clientDTO;
                }).collect(Collectors.toList())
        );
    }

    @PutMapping("/on")
    public ResponseEntity<HttpResponse> enableClient(@RequestParam Integer clientId) throws ClientNotFoundException, MethodNotAllowedException {
        clientService.enableClient(clientId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Client enabled successfully"),
                HttpStatus.OK
        );
    }

    @PutMapping("/off")
    public ResponseEntity<HttpResponse> disableClient(@RequestParam Integer clientId) throws ClientNotFoundException, MethodNotAllowedException {
        clientService.disableClient(clientId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Client disabled successfully"),
                HttpStatus.OK
        );
    }

    @PutMapping("/update/{clientId}")
    public ResponseEntity<HttpResponse> updateClient(@PathVariable Integer clientId, @RequestBody ClientDTO clientDTO) throws ClientNotFoundException {
        clientService.updateClient(clientId, clientDTO);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Client updated successfully"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<HttpResponse> deleteClient(@PathVariable Integer clientId) throws ClientNotFoundException {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Client deleted successfully"),
                HttpStatus.OK
        );
    }
}
