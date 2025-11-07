package com.livraison.dto;

import com.livraison.entity.enums.TypeVehicules;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private TypeVehicules type;
    private double capacitePoids;
    private double capaciteVolume;
    private int livraisonsMax;
}
