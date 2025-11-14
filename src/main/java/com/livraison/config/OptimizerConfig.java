package com.livraison.config;

import com.livraison.optimizer.*;
import com.livraison.entity.enums.OptimizerType;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class OptimizerConfig {

    @Value("${optimizer.type}")
    private OptimizerType optimizerType;

    @Bean
    @Primary
    public TourOptimizer tourOptimizer(
            NearestNeighborOptimizer nearestNeighborOptimizer,
            ClarkeWrightOptimizer clarkeWrightOptimizer,
            @Qualifier("customAIOptimizer") ObjectProvider<AIOptimizer> aiOptimizerProvider
    ) {
        System.out.println("\n=== Configuration de l'optimiseur ===");
        System.out.println("Optimizer sélectionné dans la config: " + optimizerType);

        try {
            return switch (optimizerType) {
                case plus_proche_voisin -> {
                    System.out.println("Utilisation de l'optimiseur: Plus proche voisin");
                    yield nearestNeighborOptimizer;
                }
                case clarke_et_wright -> {
                    System.out.println("Utilisation de l'optimiseur: Clarke & Wright");
                    yield clarkeWrightOptimizer;
                }
                case ai_optimizer -> {
                    System.out.println("Tentative d'initialisation de l'AI Optimizer...");
                    AIOptimizer ai = aiOptimizerProvider.getIfAvailable();
                    if (ai != null) {
                        System.out.println("AI Optimizer initialisé avec succès");
                        yield ai;
                    }
                    System.out.println("AI Optimizer non disponible → Fallback sur l'optimiseur du plus proche voisin");
                    yield nearestNeighborOptimizer;
                }
            };
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de l'optimiseur: " + e.getMessage());
            System.err.println("Fallback sur l'optimiseur du plus proche voisin");
            return nearestNeighborOptimizer;
        }
    }

    @Bean
    public NearestNeighborOptimizer nearestNeighborOptimizer() {
        return new NearestNeighborOptimizer();
    }

    @Bean
    public ClarkeWrightOptimizer clarkeWrightOptimizer() {
        return new ClarkeWrightOptimizer();
    }

    @Bean
    @ConditionalOnProperty(name = "optimizer.type", havingValue = "ai_optimizer")
    public ChatClient chatClient(OllamaChatModel model) {
        System.out.println("Création du ChatClient avec le modèle: " + model);
        return ChatClient.builder(model)
                .defaultSystem("You are an AI assistant specialized in route optimization.")
                .build();
    }

    @Bean("customAIOptimizer")
    public AIOptimizer customAIOptimizer(ChatClient chatClient) {
        if (chatClient == null) {
            System.out.println("ChatClient non disponible, AIOptimizer non créé");
            return null;
        }
        return new AIOptimizer(chatClient);
    }

}
