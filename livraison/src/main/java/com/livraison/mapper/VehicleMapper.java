package com.livraison.mapper;

import com.livraison.dto.VehicleDTO;
import com.livraison.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) return null;
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .type(vehicle.getType())
                .capacitePoids(vehicle.getMaxWeightKg())
                .capaciteVolume(vehicle.getMaxVolumeM3())
                .livraisonsMax(vehicle.getMaxDeliveries())
                .build();
    }

    public Vehicle toEntity(VehicleDTO dto) {
        if (dto == null) return null;
        return Vehicle.builder()
                .id(dto.getId())
                .type(dto.getType())
                .maxWeightKg(dto.getCapacitePoids())
                .maxVolumeM3(dto.getCapaciteVolume())
                .maxDeliveries(dto.getLivraisonsMax())
                .build();
    }
}
