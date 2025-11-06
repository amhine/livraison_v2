package com.livraison.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorTest {

    @Test
    void calculateDistance_samePoint_isZero() {
        double d = DistanceCalculator.calculateDistance(48.8566, 2.3522, 48.8566, 2.3522);
        assertEquals(0.0, d, 1e-9);
    }

    @Test
    void calculateDistance_parisToLyon_positive() {
        double d = DistanceCalculator.calculateDistance(48.8566, 2.3522, 45.7640, 4.8357);
        assertTrue(d > 0);
        assertTrue(d < 500);
    }
}
