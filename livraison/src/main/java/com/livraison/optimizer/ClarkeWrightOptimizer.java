package com.livraison.optimizer;

import com.livraison.entity.Customer;
import com.livraison.entity.Warehouse;
import com.livraison.util.DistanceCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClarkeWrightOptimizer implements TourOptimizer {

    @Override
    public List<Customer> optimize(Warehouse warehouse, List<Customer> deliveries) {
        record Saving(Customer i, Customer j, double value) {}

        List<Saving> savings = new ArrayList<>();

        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                double d0i = DistanceCalculator.calculateDistance(
                        warehouse.getLatitude(), warehouse.getLongitude(),
                        deliveries.get(i).getLatitude(), deliveries.get(i).getLongitude());

                double d0j = DistanceCalculator.calculateDistance(
                        warehouse.getLatitude(), warehouse.getLongitude(),
                        deliveries.get(j).getLatitude(), deliveries.get(j).getLongitude());

                double dij = DistanceCalculator.calculateDistance(
                        deliveries.get(i).getLatitude(), deliveries.get(i).getLongitude(),
                        deliveries.get(j).getLatitude(), deliveries.get(j).getLongitude());

                double saving = d0i + d0j - dij;
                savings.add(new Saving(deliveries.get(i), deliveries.get(j), saving));
            }
        }

        savings.sort(Comparator.comparingDouble(Saving::value).reversed());
        List<Customer> result = new ArrayList<>();
        for (Saving s : savings) {
            if (!result.contains(s.i())) result.add(s.i());
            if (!result.contains(s.j())) result.add(s.j());
        }

        return result;
    }
}
