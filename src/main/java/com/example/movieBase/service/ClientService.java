package com.example.movieBase.service;

import com.example.movieBase.exception.DataNotFoundException;
import com.example.movieBase.model.Client;
import com.example.movieBase.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepo;

    public List<Client> getClients(){
        return clientRepo.findAll();
    }

    public Client findClientById(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (optionalClient.isEmpty()) {
            throw new DataNotFoundException("Client with ID "+id+" not found in the database");
        }
        return optionalClient.get();
    }

    public Client findClientByName(String name) {
        Optional<Client> optionalClient = clientRepo.findByName(name);
        if (optionalClient.isEmpty()) {
            throw new DataNotFoundException("Client with name "+name+" not found in the database");
        }
        return optionalClient.get();
    }

    public Client findClientByEmail(String email) {
        Optional<Client> optionalClient = clientRepo.findByEmail(email);
        if (optionalClient.isEmpty()) {
            throw new DataNotFoundException("Client with email "+email+" not found in the database");
        }
        return optionalClient.get();
    }

    public List<Client> findClientByAge(Integer age) {
        Optional<List<Client>> optionalClients = clientRepo.findByAge(age);
        if (optionalClients.isEmpty() || optionalClients.get().size()==0) {
            throw new DataNotFoundException("Client(s) with age "+age+" not found in the database");
        }
        return optionalClients.get();
    }

    public Client addClient(Client client){
        return clientRepo.save(client);
    }

    public Client updateClient(Client client, Long id) {
        Client optionalClient = findClientById(id);
        Client clientToUpdate = optionalClient;

        boolean isNameProvided = client.getName() != null && !client.getName().isEmpty() && !client.getName().isBlank();
        boolean isAgeProvided = client.getAge() != null && client.getAge() >=3;
        boolean isEmailProvided = client.getEmail() != null && !client.getEmail().isEmpty() && !client.getEmail().isBlank();


        if (isNameProvided) {
            clientToUpdate.setName(client.getName());
        }
        if (isAgeProvided) {
            clientToUpdate.setAge(client.getAge());
        }
        if (isEmailProvided) {
            clientToUpdate.setEmail(client.getEmail());
        }
        return clientRepo.save(clientToUpdate);
    }

    public void deleteClient(Long id) {
        Client clientToDelete = findClientById(id);
        clientRepo.delete(clientToDelete);
    }
}
