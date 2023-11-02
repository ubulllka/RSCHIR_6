package com.example.rschir6.services;


import com.example.rschir6.models.Telephone;
import com.example.rschir6.repositories.TelephoneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelephoneService {
    private final TelephoneRepository telephoneRepository;

    public TelephoneService(TelephoneRepository telephoneRepository) {
        this.telephoneRepository = telephoneRepository;
    }

    public Iterable<Telephone> getAll() {
        return telephoneRepository.findAll();
    }

    public Telephone getOne(int id) {
        return telephoneRepository.findById(id).get();
    }

    public void save(Telephone telephone) {
        telephoneRepository.save(telephone);
    }

    public String update(int id, Telephone newTelephone){
        Optional<Telephone> optionalTelephone = telephoneRepository.findById(id);
        if (optionalTelephone.isEmpty()){
            return "Data not update.";
        }
        newTelephone.setId(id);
        telephoneRepository.save(newTelephone);
        return "Data update.";
    }

    public String delete(int id){
        Optional<Telephone> optionalBook = telephoneRepository.findById(id);
        if (optionalBook.isEmpty()){
            return "Data no.";
        }
        telephoneRepository.delete(optionalBook.get());
        return "Data delete.";
    }
}
