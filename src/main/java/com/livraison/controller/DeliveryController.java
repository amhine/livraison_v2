package com.livraison.controller;

import com.livraison.dto.DeliveryDTO;
import com.livraison.dto.StatusUpdateRequest;
import com.livraison.service.DeliveryService;
import com.livraison.entity.enums.StatusLivraison;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    private static final Set<String> ALLOWED_SORT = Set.of("id","nameClient","status","latitude","longitude");

    private Pageable sanitize(Pageable pageable) {
        if (pageable == null || pageable.getSort() == null) return pageable;
        Sort cleaned = Sort.by(pageable.getSort().stream()
                .map(order -> {
                    String prop = order.getProperty().replace("[", "").replace("]", "").replace("\"", "");
                    if (!ALLOWED_SORT.contains(prop)) {
                        prop = "id";
                    }
                    return new Sort.Order(order.getDirection(), prop);
                })
                .toList());
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), cleaned);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DeliveryDTO>> getDeliveriesPage(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Pageable p = sanitize(pageable);
        return ResponseEntity.ok(deliveryService.getDeliveries(p));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DeliveryDTO>> searchByName(
            @RequestParam(required = false, defaultValue = "") String name,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Pageable p = sanitize(pageable);
        return ResponseEntity.ok(deliveryService.searchByName(name, p));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<DeliveryDTO>> findByStatus(
            @RequestParam StatusLivraison status,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Pageable p = sanitize(pageable);
        return ResponseEntity.ok(deliveryService.findByStatus(status, p));
    }

    @GetMapping("/geo")
    public ResponseEntity<Page<DeliveryDTO>> searchByGeo(
            @RequestParam double minLat,
            @RequestParam double maxLat,
            @RequestParam double minLon,
            @RequestParam double maxLon,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Pageable p = sanitize(pageable);
        return ResponseEntity.ok(deliveryService.searchByGeo(minLat, maxLat, minLon, maxLon, p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @PostMapping("")
    public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.createDelivery(deliveryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> updateDelivery(
            @PathVariable Long id,
            @RequestBody DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, deliveryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(deliveryService.updateStatus(id, request.getStatus()));
    }
}