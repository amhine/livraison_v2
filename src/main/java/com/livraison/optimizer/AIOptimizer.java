package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        if (deliveries.size() <= 2) {
            return deliveries;
        }

        if (chatClient == null) {
            System.out.println("ChatClient is null, returning original delivery order");
            return deliveries;
        }

        // Construire une description claire des livraisons
        StringBuilder deliveriesDesc = new StringBuilder();
        for (Delivery d : deliveries) {
            deliveriesDesc.append(String.format("- Delivery %s: lat=%.6f, lon=%.6f\n",
                    d.getId(), d.getLatitude(), d.getLongitude()));
        }

        String promptText = String.format(
                "You are a route optimization expert. Optimize the delivery route starting from the warehouse.\n\n" +
                        "WAREHOUSE LOCATION:\n" +
                        "- Latitude: %.6f\n" +
                        "- Longitude: %.6f\n\n" +
                        "DELIVERIES TO OPTIMIZE:\n%s\n" +
                        "TASK:\n" +
                        "1. Calculate the optimal visiting order to minimize total distance\n" +
                        "2. Start from the warehouse, visit all deliveries, and return to warehouse\n" +
                        "3. Return ONLY the delivery IDs in optimal order, separated by commas\n\n" +
                        "IMPORTANT: Respond with ONLY the delivery IDs in order, nothing else.\n" +
                        "Example format: %s\n" +
                        "Your response:",
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                deliveriesDesc.toString(),
                deliveries.stream().map(d -> d.getId().toString()).collect(Collectors.joining(","))
        );

        try {
            System.out.println("\n=== AI OPTIMIZER - PROMPT ===");
            System.out.println(promptText);

            String llmResponse = chatClient.prompt()
                    .user(promptText)
                    .call()
                    .content();

            System.out.println("\n=== AI OPTIMIZER - RESPONSE ===");
            System.out.println(llmResponse);

            List<Long> orderedIds = extractOrderedIdsFromResponse(llmResponse);

            System.out.println("\n=== AI OPTIMIZER - EXTRACTED IDS ===");
            System.out.println(orderedIds);

            if (orderedIds.isEmpty()) {
                System.err.println("Aucun ID extrait, retour à l'ordre original");
                return deliveries;
            }

            // Reconstruire la liste dans l'ordre optimisé
            List<Delivery> optimized = new ArrayList<>();
            for (Long id : orderedIds) {
                deliveries.stream()
                        .filter(d -> d.getId().equals(id))
                        .findFirst()
                        .ifPresent(optimized::add);
            }

            // Ajouter les livraisons manquantes (au cas où)
            for (Delivery d : deliveries) {
                if (!optimized.contains(d)) {
                    optimized.add(d);
                }
            }

            System.out.println("\n=== AI OPTIMIZER - FINAL ORDER ===");
            optimized.forEach(d -> System.out.println("Delivery " + d.getId()));

            return optimized;

        } catch (Exception e) {
            System.err.println("Erreur lors de l'optimisation AI: " + e.getMessage());
            e.printStackTrace();
            return deliveries;
        }
    }

    private List<Long> extractOrderedIdsFromResponse(String response) {
        List<Long> ids = new ArrayList<>();

        try {
            // Nettoyer la réponse
            String cleaned = response
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .replaceAll("[\\[\\]{}\"']", "")
                    .replaceAll("orderedDeliveries:", "")
                    .replaceAll("\\s+", "")
                    .trim();

            System.out.println("Cleaned response: " + cleaned);

            // Extraire tous les nombres
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(cleaned);

            while (matcher.find()) {
                try {
                    ids.add(Long.parseLong(matcher.group()));
                } catch (NumberFormatException e) {
                    // Ignorer les nombres invalides
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'extraction des IDs: " + e.getMessage());
        }

        return ids;
    }
}