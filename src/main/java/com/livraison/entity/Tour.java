package com.livraison.entity;

import com.livraison.entity.enums.TourStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

import com.livraison.entity.enums.OptimizerType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double distanceTotale;

    @Enumerated(EnumType.STRING)
    private OptimizerType optimizerUsed;

    @Enumerated(EnumType.STRING)
    private TourStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouses warehouses;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @OrderColumn(name = "delivery_order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Delivery> deliveries;
}
