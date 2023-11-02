package com.example.rschir6.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "basket_details")
@NoArgsConstructor
public class BasketDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private int quantity;

    @Column
    private int product_id;

    @Column()
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIgnore
    @JoinColumn(name = "basket_id", nullable = false, referencedColumnName = "id")
    private Basket basket;

}
