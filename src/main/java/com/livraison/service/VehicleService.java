package com.livraison.service;

import com.livraison.dto.VehicleDTO;
import java.util.List;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO dto);
    VehicleDTO updateVehicle(Long id, VehicleDTO dto);
    void deleteVehicle(Long id);
    VehicleDTO getVehicleById(Long id);
    List<VehicleDTO> getAllVehicles();
    List<VehicleDTO> findByCapacitePoidsOrderByCapaciteVolumeDESC();
}
