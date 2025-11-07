package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;

import java.util.List;

public interface TourOptimizer {
    List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries);
}
