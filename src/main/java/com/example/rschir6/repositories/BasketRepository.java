package com.example.rschir6.repositories;

import com.example.rschir6.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Optional<Basket> findBasketByClient_Id(int id);
}
