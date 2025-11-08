package com.livraison.mapper;

import com.livraison.dto.VehicleDTO;
import com.livraison.entity.Vehicle;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Component
public class VehicleMapper {

    public VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) return null;
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .type(vehicle.getType())
                .capacitePoids(vehicle.getCapacitePoids())
                .capaciteVolume(vehicle.getCapaciteVolume())
                .livraisonsMax(vehicle.getLivraisonsMax())
                .build();
    }

    public Vehicle toEntity(VehicleDTO dto) {
        if (dto == null) return null;
        return Vehicle.builder()
                .id(dto.getId())
                .type(dto.getType())
                .capacitePoids(dto.getCapacitePoids())
                .capaciteVolume(dto.getCapaciteVolume())
                .livraisonsMax(dto.getLivraisonsMax())
                .build();
    }
}
