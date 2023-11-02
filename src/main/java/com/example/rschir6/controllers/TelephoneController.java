package com.example.rschir6.controllers;


import com.example.rschir6.models.Telephone;
import com.example.rschir6.services.TelephoneService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telephones")
public class TelephoneController {
    private final TelephoneService telephoneService;

    public TelephoneController(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Telephone> getAll() {
        return telephoneService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Telephone getOne(@PathVariable int id) {
        return telephoneService.getOne(id);
    }

    @PostMapping()
    @ResponseBody
    public String add(@RequestBody Telephone telephone){
        telephoneService.save(telephone);
        return "Data save.";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable int id, @RequestBody Telephone telephone) {
        return telephoneService.update(id, telephone);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        return telephoneService.delete(id);
    }
}
