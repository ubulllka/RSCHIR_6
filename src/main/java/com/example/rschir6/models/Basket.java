package com.example.rschir6.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "basket")
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "total")
    private int total;

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<BasketDetail> basketDetails;
}
