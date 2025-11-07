package com.livraison.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OptimizeTourRequest {
    private Long vehicleId;
    private Long warehouseId;
    private LocalDate date;
    private List<Long> deliveryIds;
}
