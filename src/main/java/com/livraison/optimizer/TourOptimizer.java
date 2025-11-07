package com.livraison.optimizer;

import com.livraison.entity.Customer;
import com.livraison.entity.Warehouse;

import java.util.List;

public interface TourOptimizer {
    List<Customer> optimize(Warehouse warehouse, List<Customer> deliveries);
}
