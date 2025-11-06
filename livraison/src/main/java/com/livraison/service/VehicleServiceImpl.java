package com.livraison.service;

import com.livraison.dto.VehicleDTO;
import com.livraison.entity.Vehicle;
import com.livraison.entity.enums.VehicleType;
import com.livraison.mapper.VehicleMapper;
import com.livraison.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleDTO createVehicle(VehicleDTO dto) {
        Vehicle vehicle = vehicleMapper.toEntity(dto);
        return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleDTO updateVehicle(Long id, VehicleDTO dto) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé avec ID: " + id));

        existing.setType(dto.getType());
        existing.setCapacitePoids(dto.getCapacitePoids());
        existing.setCapaciteVolume(dto.getCapaciteVolume());
        existing.setLivraisonsMax(dto.getLivraisonsMax());

        return vehicleMapper.toDTO(vehicleRepository.save(existing));
    }

    @Override

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Véhicule non trouvé avec ID: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    public VehicleDTO getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé avec ID: " + id));
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDTO> findByCapacitePoidsOrderByCapaciteVolumeDESC() {
        return List.of();
    }


}
