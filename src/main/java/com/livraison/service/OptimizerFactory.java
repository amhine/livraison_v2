package com.livraison.service;

import com.livraison.entity.enums.OptimizerType;
import com.livraison.optimizer.AIOptimizer;
import com.livraison.optimizer.ClarkeWrightOptimizer;
import com.livraison.optimizer.NearestNeighborOptimizer;
import com.livraison.optimizer.TourOptimizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class OptimizerFactory {

    private final Map<OptimizerType, ObjectProvider<? extends TourOptimizer>> optimizers;

    public OptimizerFactory(
            ObjectProvider<NearestNeighborOptimizer> nearestOptimizer,
            ObjectProvider<ClarkeWrightOptimizer> clarkeOptimizer,
            ObjectProvider<AIOptimizer> aiOptimizer
    ) {
        this.optimizers = new EnumMap<>(OptimizerType.class);
        this.optimizers.put(OptimizerType.plus_proche_voisin, nearestOptimizer);
        this.optimizers.put(OptimizerType.clarke_et_wright, clarkeOptimizer);
        this.optimizers.put(OptimizerType.ai_optimizer, aiOptimizer);
    }

    public TourOptimizer getOptimizer(OptimizerType type) {
        ObjectProvider<? extends TourOptimizer> provider = optimizers.get(type);
        return provider != null ? provider.getIfAvailable() : null;
    }
}