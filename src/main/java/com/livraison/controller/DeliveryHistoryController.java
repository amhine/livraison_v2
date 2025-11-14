package com.livraison.controller;


import com.livraison.dto.DeliveryHistoryDTO;
import com.livraison.service.DeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveryhistory")
@RequiredArgsConstructor
public class DeliveryHistoryController {
    private final DeliveryHistoryService deliveryHistoryService;

    @GetMapping
    public ResponseEntity<List<DeliveryHistoryDTO>> getAll() {
        return ResponseEntity.ok(deliveryHistoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<DeliveryHistoryDTO> create(@RequestBody DeliveryHistoryDTO dto) {
        return ResponseEntity.ok(deliveryHistoryService.create(dto));
    }

    @GetMapping("/delivery/{id}")
    public ResponseEntity<DeliveryHistoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryHistoryService.getHistoryByDeliveryId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deliveryHistoryService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
