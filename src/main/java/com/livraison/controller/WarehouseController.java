package com.livraison.controller;

import com.livraison.dto.WarehouseDTO;
import com.livraison.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<WarehouseDTO> create(@RequestBody WarehouseDTO dto) {
        return ResponseEntity.ok(warehouseService.createWarehouse(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> update(@PathVariable Long id, @RequestBody WarehouseDTO dto) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getAll() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }
}
