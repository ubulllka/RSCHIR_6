package com.example.rschir6.repositories;

import com.example.rschir6.models.BasketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketDetailsRepository  extends JpaRepository<BasketDetail, Integer> {
    List<BasketDetail> findBasketDetailsByBasket_Id(Integer id);
}
