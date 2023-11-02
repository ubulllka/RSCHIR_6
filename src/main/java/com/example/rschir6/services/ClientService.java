package com.example.rschir6.services;

import com.example.rschir6.models.Client;
import com.example.rschir6.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getOne(int id) {
        return clientRepository.findById(id).get();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public String update(int id, Client newClient){
        Optional<Client> optionalTelephone = clientRepository.findById(id);
        if (optionalTelephone.isEmpty()){
            return "Data not update.";
        }
        newClient.setId(id);
        clientRepository.save(newClient);
        return "Data update.";
    }

    public String delete(int id){
        Optional<Client> optionalBook = clientRepository.findById(id);
        if (optionalBook.isEmpty()){
            return "Data no.";
        }
        clientRepository.delete(optionalBook.get());
        return "Data delete.";
    }
}
