package com.livraison.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private LocalTime heureOuverture;
    private LocalTime heureFermeture;
}
