package com.duk.dukscoffee.services.interfaces;

import java.util.List;

import com.duk.dukscoffee.entities.Client;
import com.duk.dukscoffee.exceptions.CardIdExistException;
import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.EmailExistException;
import com.duk.dukscoffee.exceptions.ParameterNotAllowedException;
import com.duk.dukscoffee.http.DTO.ClientDTO;

public interface IClientService {
    
    public ClientDTO createClient(ClientDTO clientDTO) throws EmailExistException, CardIdExistException;
    public ClientDTO getClientInfoDetails(Integer clientId) throws ClientNotFoundException;
    public List<Client> getClients();
    public void disableClient(Integer clientId) throws ClientNotFoundException;
    public void enableClient(Integer clientId) throws ClientNotFoundException;
    public ClientDTO updateClient(Integer clientId, ClientDTO clientDTO) throws ClientNotFoundException;
    public void deleteClient(Integer clientId) throws ClientNotFoundException;
    public List<Client> filterClients(String attributeName, String attributeValue) throws ParameterNotAllowedException;
    



}
