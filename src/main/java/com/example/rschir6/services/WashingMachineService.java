package com.example.rschir6.services;

import com.example.rschir6.models.WashingMachine;
import com.example.rschir6.repositories.WashingMachineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WashingMachineService {
    private final WashingMachineRepository washingMachineRepository;

    public WashingMachineService(WashingMachineRepository washingMachineRepository) {
        this.washingMachineRepository = washingMachineRepository;
    }

    public Iterable<WashingMachine> getAll() {
        return washingMachineRepository.findAll();
    }

    public WashingMachine getOne(int id) {
        return washingMachineRepository.findById(id).get();
    }

    public void save(WashingMachine washingMachine) {
        washingMachineRepository.save(washingMachine);
    }

    public String update(int id, WashingMachine newWashingMachine){
        Optional<WashingMachine> optionalWashingMachine = washingMachineRepository.findById(id);
        if (optionalWashingMachine.isEmpty()){
            return "Data not update.";
        }
        newWashingMachine.setId(id);
        washingMachineRepository.save(newWashingMachine);
        return "Data update.";
    }

    public String delete(int id){
        Optional<WashingMachine> optionalWashingMachine = washingMachineRepository.findById(id);
        if (optionalWashingMachine.isEmpty()){
            return "Data no.";
        }
        washingMachineRepository.delete(optionalWashingMachine.get());
        return "Data delete.";
    }
}
