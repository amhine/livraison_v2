package com.livraison.optimizer;

import com.livraison.entity.Customer;
import com.livraison.entity.Warehouse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NearestNeighborOptimizerTest {

    @Test
    void optimize_ordersAllDeliveries() {
        Warehouse wh = Warehouse.builder()
                .latitude(48.8566)
                .longitude(2.3522)
                .build();

        Customer d1 = Customer.builder().id(1L).latitude(48.8606).longitude(2.3376).build();
        Customer d2 = Customer.builder().id(2L).latitude(48.8570).longitude(2.3709).build();
        Customer d3 = Customer.builder().id(3L).latitude(48.8530).longitude(2.3499).build();

        NearestNeighborOptimizer opt = new NearestNeighborOptimizer();
        List<Customer> ordered = opt.optimize(wh, List.of(d1, d2, d3));

        assertEquals(3, ordered.size());
        assertTrue(ordered.containsAll(List.of(d1, d2, d3)));
        assertEquals(d3, ordered.get(0));
    }
}
