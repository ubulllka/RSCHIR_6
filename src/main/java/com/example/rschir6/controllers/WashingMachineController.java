package com.example.rschir6.controllers;

import com.example.rschir6.models.WashingMachine;
import com.example.rschir6.services.WashingMachineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/washing-machines")
public class WashingMachineController {
    private final WashingMachineService washingMachineService;

    public WashingMachineController(WashingMachineService washingMachineService) {
        this.washingMachineService = washingMachineService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<WashingMachine> getAll() {
        return washingMachineService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public WashingMachine getOne(@PathVariable int id) {
        return washingMachineService.getOne(id);
    }

    @PostMapping()
    @ResponseBody
    public String add(@RequestBody WashingMachine washingMachine){
        washingMachineService.save(washingMachine);
        return "Data save.";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable int id, @RequestBody WashingMachine washingMachine) {
        return washingMachineService.update(id, washingMachine);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        return washingMachineService.delete(id);
    }
}
