package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.dto.OptimizeTourRequest;
import com.livraison.entity.enums.TourStatus;
import com.livraison.optimizer.TourOptimizer;

import java.util.List;

public interface TourService {
    List<TourDTO> findAll();
    TourDTO findById(Long id);
    TourDTO save(TourDTO dto);
    TourDTO update(Long id, TourDTO dto);
    void delete(Long id);

    TourDTO optimizeTour(Long id);

    double getTotalDistance(Long id);
    double getTotalDistanceAfterOptimization(Long id, TourOptimizer optimizer);

    TourDTO createAndOptimize(OptimizeTourRequest req);

    TourDTO updateStatus(Long id, TourStatus status);
}