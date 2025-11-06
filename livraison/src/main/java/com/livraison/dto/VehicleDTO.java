package com.livraison.dto;

import com.livraison.entity.enums.VehicleType ;
import com.livraison.entity.enums.VehicleType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private VehicleType type;
    private double capacitePoids;
    private double capaciteVolume;
    private int livraisonsMax;
}
