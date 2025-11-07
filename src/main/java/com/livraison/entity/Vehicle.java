package com.livraison.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.livraison.entity.enums.TypeVehicules;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeVehicules type;

    private double capacitePoids;
    private double capaciteVolume;
    private int livraisonsMax;

    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tour> tours;
}

