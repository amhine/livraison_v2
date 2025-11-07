package com.livraison.service;

import com.livraison.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseService {
    WarehouseDTO createWarehouse(WarehouseDTO dto);
    WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto);
    void deleteWarehouse(Long id);
    WarehouseDTO getWarehouseById(Long id);
    List<WarehouseDTO> getAllWarehouses();
}
