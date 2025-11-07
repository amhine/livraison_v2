package com.livraison.entity;

import com.livraison.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.livraison.entity.enums.VehicleType ;

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
    private VehicleType type;

    private double maxWeightKg;
    private double maxVolumeM3;
    private int maxDeliveries;

    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tour> tours;
}

