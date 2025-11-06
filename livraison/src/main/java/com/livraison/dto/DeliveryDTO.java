package com.livraison.dto;

import com.livraison.entity.enums.DeliveryStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class DeliveryDTO {
    private Long id;
    private String nameClient;
    private String address;
    private double latitude;
    private double longitude;
    private double poids;
    private double volume;
    private DeliveryStatus status;
    private Long tourId; 
}
