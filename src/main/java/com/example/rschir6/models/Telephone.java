package com.example.rschir6.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "telephone")
@NoArgsConstructor
public class Telephone {
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
    private com.example.rschir6.models.TypeProduct typeProduct = TypeProduct.ELECTRONICS;

    @Column(name = "number_seller")
    private int numberSeller;

    @Column(name = "all_quantity")
    private int allQuantity;
}
