package com.duk.dukscoffee.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

import com.duk.dukscoffee.entities.Client;
import com.duk.dukscoffee.exceptions.CardIdExistException;
import com.duk.dukscoffee.exceptions.ClientExistException;
import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.EmailExistException;
import com.duk.dukscoffee.exceptions.ParameterNotAllowedException;
import com.duk.dukscoffee.http.DTO.ClientDTO;
import com.duk.dukscoffee.respositories.ClientRepository;
import com.duk.dukscoffee.services.interfaces.IClientService;

@Service
public class ClientService implements IClientService {

    public static final String IS_ALREADY_USE = "The %s is already use";
    public static final String INVALID_GENDER = "Invalid gender. Should be 'M' or 'F'";
    public static final String IS_NOT_FOUND = "The %s is not found";
    public static final String IS_NOT_ALLOWED = "The %s is not allowed";

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) throws EmailExistException, CardIdExistException {
        
        ifClientExists(clientDTO.getCardId(), clientDTO.getEmail());
        validateGender(clientDTO.getGender());

        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);

        client.setActive(true);
        client.setLastVisit(new Date());
        
        Client newClient = clientRepository.save(client);
        ClientDTO newClientDTO = new ClientDTO();
        BeanUtils.copyProperties(newClient, newClientDTO);

        return newClientDTO;

    }
    @Override
    public ClientDTO getClientInfoDetails(Integer clientId) throws ClientNotFoundException{
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            throw new ClientNotFoundException(String.format(IS_NOT_FOUND, "client").toUpperCase());
        }

        ClientDTO gottenClient = new ClientDTO();
        BeanUtils.copyProperties(client, gottenClient);

        return gottenClient;
    }
    @Override
    public List<Client> getClients(){
        return (List<Client>) clientRepository.findAll();
    }
    @Override
    public void disableClient(Integer clientId) throws ClientNotFoundException{
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
             throw new ClientNotFoundException(String.format(IS_NOT_FOUND, "client").toUpperCase());  
        }
        
        if (client.isActive()== false) {
            throw new MethodNotAllowedException("YOU CAN'T DISABLE THE CLIENT BECAUSE THIS IS CURRENTLY DISABLED", null);
        }

        client.setActive(false);

        clientRepository.save(client);

    }
    @Override
    public void enableClient(Integer clientId) throws ClientNotFoundException{
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
             throw new ClientNotFoundException(String.format(IS_NOT_FOUND, "client").toUpperCase());  
        }
        
        if (client.isActive()== true) {
            throw new MethodNotAllowedException("YOU CAN'T ENABLE THE CLIENT BECAUSE THIS IS CURRENTLY ENABLED", null);
        }

        client.setActive(true);

        clientRepository.save(client);

    }

    @Override
    public ClientDTO updateClient(Integer clientId, ClientDTO clientDTO) throws ClientNotFoundException{
         validateGender(clientDTO.getGender());
        Client client = clientRepository.findById(clientId).orElse(null);
        
        if (client == null) {
            throw new ClientNotFoundException(String.format(IS_NOT_FOUND, "client").toUpperCase());
        }

        BeanUtils.copyProperties(clientDTO, client);
        
        client.setActive(true);
        client.setLastVisit(new Date());
        client.setId(clientId);
        
        Client updatedClient = clientRepository.save(client);
        ClientDTO updatedClientDTO = new ClientDTO();
        BeanUtils.copyProperties(updatedClient, updatedClientDTO);
        return updatedClientDTO;
    }

    @Override
    public void deleteClient(Integer clientId) throws ClientNotFoundException{
         Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            throw new ClientNotFoundException(String.format(IS_NOT_FOUND, "client").toUpperCase());
        }
        clientRepository.delete(client);
    }

    @Override
    public List<Client> filterClients(String attributeName, String attributeValue) throws ParameterNotAllowedException {
        List<Client> filteredClients = null;

        switch (attributeName.toLowerCase()) {
            case "id":
                filteredClients = filterById(attributeValue);
                break;
            case "name":
                filteredClients = clientRepository.findByName(attributeValue);
                break;
            case "lastname":
                filteredClients = clientRepository.findByLastName(attributeValue);
                break;
            case "email":
                filteredClients = filterByEmail(attributeValue);
                break;
            case "birthday":
                filteredClients = filterByBirthDay(attributeValue);
                break;
            case "lastvisit":
                filteredClients = filterByLastVisit(attributeValue);
                break;
            default:
                throw new ParameterNotAllowedException(String.format(IS_NOT_ALLOWED, "param").toUpperCase());
                
        }

        return filteredClients;
    }

   



     /************************************** CLASS METHOD  *******************************************/
    private void ifClientExists(String idCard, String email) throws EmailExistException, CardIdExistException{
        Client client = clientRepository.findByCardIdOrEmail(idCard, email).orElse(null);
        if(client == null) return;
        
        if(client.getEmail().equalsIgnoreCase(email))
        throw new EmailExistException(String.format(IS_ALREADY_USE, "email").toUpperCase());

        if(client.getCardId().equalsIgnoreCase(idCard))
        throw new  EmailExistException(String.format(IS_ALREADY_USE, "idCard").toUpperCase());
    }

    private void validateGender(char gender) {
        if (gender != 'M' && gender != 'F') {
            throw new IllegalArgumentException(INVALID_GENDER);
        }
    }
    
     private List<Client> filterById(String id) {
        Optional<Client> client = clientRepository.findById(Integer.parseInt(id));
        return client.map(List::of).orElse(List.of());
    }

    private List<Client> filterByEmail(String email) {
        Optional<Client> client = clientRepository.findByEmail(email);
        return client.map(List::of).orElse(List.of());
    }

    private List<Client> filterByBirthDay(String birthDay) {
        Date parsedBirthDay = new Date();
        return clientRepository.findByBirthDay(parsedBirthDay);
    }

    private List<Client> filterByLastVisit(String lastVisit) {
        Date parsedLastVisit = new Date();
        return clientRepository.findByLastVisit(parsedLastVisit);
    }

}
