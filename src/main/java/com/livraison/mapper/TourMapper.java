package com.livraison.mapper;

import com.livraison.dto.TourDTO;
import com.livraison.entity.*;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class TourMapper {

    public TourDTO toDTO(Tour tour) {
        if (tour == null) return null;

        TourDTO dto = new TourDTO();
        dto.setId(tour.getId());
        dto.setDate(tour.getDate());
        dto.setDistanceTotale(tour.getDistanceTotale());
        dto.setOptimizerUsed(tour.getOptimizerUsed());
        dto.setVehicleId(tour.getVehicle() != null ? tour.getVehicle().getId() : null);
        dto.setWarehouseId(tour.getWarehouses() != null ? tour.getWarehouses().getId() : null);
        dto.setDeliveryIds(tour.getDeliveries() != null ?
                tour.getDeliveries().stream().map(Delivery::getId).collect(Collectors.toList()) : null);
        return dto;
    }

    public Tour toEntity(TourDTO dto, Vehicle vehicle, Warehouses warehouse, java.util.List<Delivery> deliveries) {
        if (dto == null) return null;

        return Tour.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .distanceTotale(dto.getDistanceTotale())
                .optimizerUsed(dto.getOptimizerUsed())
                .vehicle(vehicle)
                .warehouses(warehouse)
                .deliveries(deliveries)
                .build();
    }
}
