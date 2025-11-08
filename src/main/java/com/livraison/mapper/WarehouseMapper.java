package com.livraison.mapper;

import com.livraison.dto.WarehouseDTO;
import com.livraison.entity.Warehouses;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public WarehouseDTO toDTO(Warehouses warehouse) {
        if (warehouse == null) return null;
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .latitude(warehouse.getLatitude())
                .longitude(warehouse.getLongitude())
                .heureOuverture(warehouse.getHeureOuverture())
                .heureFermeture(warehouse.getHeureFermeture())
                .build();
    }

    public static Warehouses toEntity(WarehouseDTO dto) {
        if (dto == null) return null;
        return Warehouses.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .heureOuverture(dto.getHeureOuverture())
                .heureFermeture(dto.getHeureFermeture())
                .build();
    }
}
