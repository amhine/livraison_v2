package com.livraison.controller;

import com.livraison.dto.TourDTO;
import com.livraison.entity.enums.OptimizerType;
import com.livraison.service.TourService;
import com.livraison.optimizer.TourOptimizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
    private final TourOptimizer tourOptimizer; // Optimizer principal configuré
    private final TourOptimizer nearestNeighborOptimizer; // Pour comparaison
    private final TourOptimizer clarkeWrightOptimizer; // Pour comparaison

    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        return ResponseEntity.ok(tourService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long id) {
        TourDTO dto = tourService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDTO) {
        return ResponseEntity.ok(tourService.save(tourDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Long id, @RequestBody TourDTO dto) {
        TourDTO updated = tourService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/optimize")
    public ResponseEntity<TourDTO> getOptimizedTour(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.optimizeTour(id));
    }

    @GetMapping("/{id}/distance")
    public ResponseEntity<Double> getTotalDistance(@PathVariable Long id) {
        double distance = tourService.getTotalDistance(id);
        return ResponseEntity.ok(distance);
    }

    @GetMapping("/{id}/compare")
    public ResponseEntity<Map<String, Object>> compareAlgorithms(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();

        double distanceNearest = tourService.getTotalDistanceAfterOptimization(id, nearestNeighborOptimizer);
        double distanceClarke = tourService.getTotalDistanceAfterOptimization(id, clarkeWrightOptimizer);
        double distanceCurrent = tourService.getTotalDistanceAfterOptimization(id, tourOptimizer);

        result.put("tourId", id);
        result.put("current_algorithm", tourOptimizer.getClass().getSimpleName());
        result.put("current_distance", distanceCurrent);
        result.put("nearest_neighbor_distance", distanceNearest);
        result.put("clarke_wright_distance", distanceClarke);

        // Déterminer le meilleur algorithme
        String bestAlgorithm = "Current";
        double bestDistance = distanceCurrent;

        if (distanceNearest < bestDistance) {
            bestAlgorithm = "Nearest Neighbor";
            bestDistance = distanceNearest;
        }
        if (distanceClarke < bestDistance) {
            bestAlgorithm = "Clarke Wright";
        }

        result.put("best_algorithm", bestAlgorithm);

        return ResponseEntity.ok(result);
    }
}