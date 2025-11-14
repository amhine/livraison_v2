package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "optimizer.type", havingValue = "plus_proche_voisin", matchIfMissing = true)
public class NearestNeighborOptimizer implements TourOptimizer {

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    @Override
    public List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries) {
        if (warehouse == null || deliveries == null || deliveries.isEmpty()) {
            return List.of();
        }

        List<Delivery> remaining = new ArrayList<>(deliveries);
        List<Delivery> tour = new ArrayList<>();

        double curLat = warehouse.getLatitude();
        double curLon = warehouse.getLongitude();

        while (!remaining.isEmpty()) {
            Delivery nearest = null;
            double bestDist = Double.MAX_VALUE;
            for (Delivery d : remaining) {
                double dist = distance(curLat, curLon, d.getLatitude(), d.getLongitude());
                if (dist < bestDist) {
                    bestDist = dist;
                    nearest = d;
                }
            }
            tour.add(nearest);
            remaining.remove(nearest);
            curLat = nearest.getLatitude();
            curLon = nearest.getLongitude();
        }
        return tour;
    }
}
