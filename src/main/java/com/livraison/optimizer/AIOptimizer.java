package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "optimizer.type", havingValue = "ai_optimizer")
@ConditionalOnBean(ChatClient.class)
public class AIOptimizer implements TourOptimizer {

    private final ChatClient chatClient;

    public AIOptimizer(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries) {
        if (warehouse == null || deliveries == null || deliveries.isEmpty()) {
            return List.of();
        }

        // Conversion des livraisons en JSON
        String deliveriesJson = deliveries.stream()
                .map(d -> String.format("{\"id\":\"%s\",\"lat\":%f,\"lon\":%f}",
                        d.getId(), d.getLatitude(), d.getLongitude()))
                .collect(Collectors.joining(",", "[", "]"));

        // Construction du prompt
        String promptText = "{\n" +
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

        try {
            // Nouvelle API de ChatClient avec Fluent API
            String llmResponse = chatClient.prompt()
                    .user(promptText)
                    .call()
                    .content();

            // Extraction des IDs ordonnés
            List<String> orderedIds = extractOrderedIdsFromJson(llmResponse);

            // Reconstruction de la liste de livraisons optimisée
            return orderedIds.stream()
                    .map(id -> deliveries.stream()
                            .filter(d -> d.getId().equals(id))
                            .findFirst()
                            .orElse(null))
                    .filter(d -> d != null)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // En cas d'erreur, retourner l'ordre original
            System.err.println("Erreur lors de l'optimisation AI: " + e.getMessage());
            return deliveries;
        }
    }

    private List<String> extractOrderedIdsFromJson(String json) {
        // TODO: utiliser Jackson/Gson pour parser proprement
        // Solution temporaire - à améliorer avec un vrai parsing JSON
        try {
            // Extraction basique - à adapter selon le format de réponse réel
            String cleanJson = json.replaceAll("[\\[\\]\"]", "").trim();
            if (cleanJson.isEmpty()) {
                return List.of();
            }
            return List.of(cleanJson.split(","));
        } catch (Exception e) {
            System.err.println("Erreur lors de l'extraction des IDs: " + e.getMessage());
            return List.of();
        }
    }
}