package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import com.livraison.util.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries) {
        List<Delivery> result = new ArrayList<>();
        List<Delivery> remaining = new ArrayList<>(deliveries);

        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        while (!remaining.isEmpty()) {
            double finalCurrentLat = currentLat;
            double finalCurrentLon = currentLon;

            Delivery nearest = remaining.stream()
                    .min((d1, d2) -> Double.compare(
                            DistanceCalculator.calculateDistance(finalCurrentLat, finalCurrentLon, d1.getLatitude(), d1.getLongitude()),
                            DistanceCalculator.calculateDistance(finalCurrentLat, finalCurrentLon, d2.getLatitude(), d2.getLongitude())
                    ))
                    .orElseThrow();

            result.add(nearest);
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
            remaining.remove(nearest);
        }

        return result;
    }
}
