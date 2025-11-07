package com.livraison.entity;

import com.livraison.entity.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private double poids;
    private double volume;
    private String preferredTimeSlot;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tour tour;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Delivery> deliveries;
}
