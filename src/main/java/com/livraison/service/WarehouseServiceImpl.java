package com.livraison.service;

import com.livraison.dto.WarehouseDTO;
import com.livraison.entity.Warehouses;
import com.livraison.mapper.WarehouseMapper;
import com.livraison.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;



    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO dto) {
        Warehouses warehouse = warehouseMapper.toEntity(dto);
        warehouseRepository.save(warehouse);
        dto.setId(warehouse.getId());
        return dto;
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto) {
        Warehouses warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setLatitude(dto.getLatitude());
        warehouse.setLongitude(dto.getLongitude());
        warehouse.setHeureOuverture(dto.getHeureOuverture());
        warehouse.setHeureFermeture(dto.getHeureFermeture());
        warehouseRepository.save(warehouse);
        return dto;
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .map(warehouseMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
