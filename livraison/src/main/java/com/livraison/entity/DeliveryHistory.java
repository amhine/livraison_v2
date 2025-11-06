package com.livraison.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long deliveryId;
    private Long customerId;
    private Long tourId;

    private LocalDate deliveryDate;
    private LocalTime plannedTime;
    private LocalTime actualTime;

    private long delayMinutes;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private String customerName;
    private String customerAddress;
    private double latitude;
    private double longitude;
    private String preferredTimeSlot;
}