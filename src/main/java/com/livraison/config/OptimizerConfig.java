package com.livraison.config;

import com.livraison.optimizer.TourOptimizer;
import com.livraison.optimizer.NearestNeighborOptimizer;
import com.livraison.optimizer.ClarkeWrightOptimizer;
import com.livraison.optimizer.AIOptimizer;
import com.livraison.entity.enums.OptimizerType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
public class OptimizerConfig {

    @Value("${optimizer.type:plus_proche_voisin}")
    private OptimizerType optimizerType;

    @Bean
    @Primary
    public TourOptimizer tourOptimizer(NearestNeighborOptimizer nearestNeighborOptimizer,
                                       ClarkeWrightOptimizer clarkeWrightOptimizer,
                                       ObjectProvider<AIOptimizer> aiOptimizerProvider) {

        return switch (optimizerType) {
            case plus_proche_voisin -> nearestNeighborOptimizer;
            case clarke_et_wright -> clarkeWrightOptimizer;
            case ai_optimizer -> {
                AIOptimizer aiOptimizer = aiOptimizerProvider.getIfAvailable();
                if (aiOptimizer != null) {
                    yield aiOptimizer;
                } else {
                    System.out.println("AI Optimizer non disponible, utilisation de Nearest Neighbor par défaut");
                    yield nearestNeighborOptimizer;
                }
            }
            default -> nearestNeighborOptimizer;
        };
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
    @ConditionalOnClass(name = "org.springframework.ai.chat.client.ChatClient")
    @ConditionalOnBean(ChatModel.class)
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("You are a helpful assistant for route optimization")
                .build();
    }

    @Bean
    @ConditionalOnBean(ChatClient.class)
    public AIOptimizer aiOptimizer(ChatClient chatClient) {
        return new AIOptimizer(chatClient);
    }
}