package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ConditionalOnProperty(name = "optimizer.type", havingValue = "clarke")
public class ClarkeWrightOptimizer implements TourOptimizer {

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    @Override
    public List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries) {
        if (warehouse == null) return Collections.emptyList();
        return calculateOptimalTour(deliveries, warehouse.getLatitude(), warehouse.getLongitude());
    }

    private List<Delivery> calculateOptimalTour(List<Delivery> deliveries, double warehouseLat, double warehouseLon) {
        int n = deliveries.size();
        if (n == 0) return Collections.emptyList();

        Map<Integer, Delivery> idxToD = new HashMap<>();
        for (int i = 0; i < n; i++) idxToD.put(i, deliveries.get(i));

        class Saving {
            int i, j;
            double saving;
        }

        List<Saving> savings = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Delivery di = idxToD.get(i);
                Delivery dj = idxToD.get(j);
                double s = distance(warehouseLat, warehouseLon, di.getLatitude(), di.getLongitude())
                        + distance(warehouseLat, warehouseLon, dj.getLatitude(), dj.getLongitude())
                        - distance(di.getLatitude(), di.getLongitude(), dj.getLatitude(), dj.getLongitude());
                Saving sv = new Saving();
                sv.i = i;
                sv.j = j;
                sv.saving = s;
                savings.add(sv);
            }
        }

        savings.sort((a, b) -> Double.compare(b.saving, a.saving)); // tri décroissant

        Map<Integer, LinkedList<Integer>> tours = new HashMap<>();
        for (int i = 0; i < n; i++) {
            LinkedList<Integer> t = new LinkedList<>();
            t.add(i);
            tours.put(i, t);
        }

        for (Saving sv : savings) {
            LinkedList<Integer> ti = findTourContaining(tours, sv.i);
            LinkedList<Integer> tj = findTourContaining(tours, sv.j);
            if (ti == null || tj == null || ti == tj) continue;
            ti.addAll(tj);
            tours.values().removeIf(list -> list == tj);
        }

        List<Delivery> result = new ArrayList<>();
        for (LinkedList<Integer> t : tours.values()) {
            for (Integer idx : t) result.add(idxToD.get(idx));
        }
        return result;
    }

    private LinkedList<Integer> findTourContaining(Map<Integer, LinkedList<Integer>> tours, int index) {
        for (LinkedList<Integer> t : tours.values()) {
            if (t.contains(index)) return t;
        }
        return null;
    }
}
