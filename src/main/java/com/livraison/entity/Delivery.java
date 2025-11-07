package com.livraison.entity;

import com.livraison.entity.enums.StatusLivraison;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameClient;
    private String address;
    private double latitude;
    private double longitude;
    private double poids;
    private double volume;

    @Enumerated(EnumType.STRING)
    private StatusLivraison status;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tour tour;
}
