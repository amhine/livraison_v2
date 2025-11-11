package com.livraison.util;

import com.livraison.util.LlmClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "llm.client.enabled", havingValue = "true", matchIfMissing=true)
public class RestLlmClient implements LlmClient {

    private final WebClient webClient;
    private final String endpoint;
    private final String apiKey;

    public RestLlmClient(org.springframework.core.env.Environment env) {
        this.endpoint = env.getProperty("llm.client.endpoint");
        this.apiKey = env.getProperty("llm.client.apiKey");
        this.webClient = WebClient.builder()
                .baseUrl(endpoint)
                .defaultHeader("Authorization", "Bearer " + (apiKey == null ? "" : apiKey))
                .build();
    }

    @Override
    public String callLlm(String jsonPrompt) {
        // Exemple POST — adapte body selon l'API LLM (OpenAI, Ollama, etc.)
        Mono<String> resp = webClient.post()
                .uri("/v1/generate") // adapter selon LLM
                .bodyValue(new JSONObject().put("input", jsonPrompt).toString())
                .retrieve()
                .bodyToMono(String.class);
        return resp.block(); // bloquant pour simplicité — en prod utiliser réactif/asynchrone correctement
    }

    @Override
    public List<String> extractOrderedIds(String llmResponse) {
        // Suppose LLM renvoie un JSON contenant orderedDeliveries: ["id1","id2",...]
        try {
            JSONObject obj = new JSONObject(llmResponse);
            JSONArray arr = obj.optJSONArray("orderedDeliveries");
            List<String> ids = new ArrayList<>();
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) ids.add(arr.getString(i));
            }
            return ids;
        } catch (Exception e) {
            return List.of();
        }
    }
}
