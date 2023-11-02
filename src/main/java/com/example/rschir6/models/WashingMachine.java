package com.example.rschir6.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "washing_machine")
@NoArgsConstructor
public class WashingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "efficiency")
    private int efficiency;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "type_product")
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct = TypeProduct.PLUMBING;

    @Column(name = "number_seller")
    private int numberSeller;

    @Column(name = "all_quantity")
    private int allQuantity;
}
