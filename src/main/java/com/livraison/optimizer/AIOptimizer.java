package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "optimizer.type", havingValue = "ai_optimizer")
public class AIOptimizer implements TourOptimizer {

    private final OllamaChatModel ollamaChatModel;

    public AIOptimizer(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @Override
    public List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries) {
        if (warehouse == null || deliveries == null || deliveries.isEmpty()) {
            return List.of();
        }

        String deliveriesJson = deliveries.stream()
                .map(d -> String.format("{\"id\":\"%s\",\"lat\":%f,\"lon\":%f}",
                        d.getId(), d.getLatitude(), d.getLongitude()))
                .collect(Collectors.joining(",", "[", "]"));

        String prompt = "{\n" +
                "  \"context\": \"Optimisation de tournées pour une flotte. Retourner un JSON structuré\",\n" +
                "  \"warehouse\": {\"lat\":" + warehouse.getLatitude() + ",\"lon\":" + warehouse.getLongitude() + "},\n" +
                "  \"deliveries\": " + deliveriesJson + ",\n" +
                "  \"constraints\": {\"vehicleCapacityKg\":1000, \"maxStops\":50},\n" +
                "  \"outputFormat\": {\n" +
                "    \"orderedDeliveries\": [\"id\"],\n" +
                "    \"recommendations\": \"string explaining reasons\",\n" +
                "    \"predictedRoutes\": [{\"from\":\"id\",\"to\":\"id\",\"distanceKm\":0.0}]\n" +
                "  }\n" +
                "}";

        String llmResponse = ollamaChatModel.call(prompt);

        List<String> orderedIds = extractOrderedIdsFromJson(llmResponse);

        return orderedIds.stream()
                .map(id -> deliveries.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null))
                .filter(d -> d != null)
                .collect(Collectors.toList());
    }

    private List<String> extractOrderedIdsFromJson(String json) {
        // TODO: utiliser Jackson/Gson pour parser proprement
        return List.of(json.replaceAll("[\\[\\]\"]", "").split(","));
    }
}
