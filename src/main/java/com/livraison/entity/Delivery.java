package com.livraison.entity;

import com.livraison.entity.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double poids;
    private double volume;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalTime plannedTime;
    private LocalTime actualTime;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}