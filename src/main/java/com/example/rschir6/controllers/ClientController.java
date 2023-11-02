package com.example.rschir6.controllers;

import com.example.rschir6.models.Client;
import com.example.rschir6.services.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Client getOne(@PathVariable int id) {
        return clientService.getOne(id);
    }

    @PostMapping()
    @ResponseBody
    public String add(@RequestBody Client client){
        clientService.save(client);
        return "Data save.";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable int id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        return clientService.delete(id);
    }
}
