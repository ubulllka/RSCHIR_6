package com.example.rschir6.controllers;

import com.example.rschir6.models.Basket;
import com.example.rschir6.models.BasketDetail;
import com.example.rschir6.services.BasketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Basket> getAll() {
        return basketService.getAll();
    }

    @GetMapping("/d")
    @ResponseBody
    public Iterable<BasketDetail> getDet() {
        return basketService.getDet();
    }

    @GetMapping("/client/{id}")
    @ResponseBody
    public Basket getOneByClientId(@PathVariable int id) {
        return basketService.getOneByClientId(id);
    }

    @DeleteMapping("/client/{id}")
    @ResponseBody
    public String deleteByClientId(@PathVariable int id) {
        return basketService.deleteByClientId(id);
    }

    @PostMapping("/client/{id}")
    @ResponseBody
    public String addProduct(@PathVariable("id") int clientId, @RequestBody BasketDetail basketDetail){
        return basketService.addProductInBasket(clientId, basketDetail.getTypeProduct(), basketDetail.getProduct_id(), basketDetail.getQuantity());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable("id") int basketDetailsId){
        return basketService.deleteProductInBasket(basketDetailsId);
    }

    @PutMapping("/{deteils_id}")
    public String update_q(@PathVariable("deteils_id") int id, @RequestParam("quantity") int quantity){
        return basketService.changeQuantity(id,quantity);
    }

    @PostMapping("/buy/{client_id}")
    public String buy(@PathVariable("client_id") int id){
        return basketService.buy(id);
    }



}
