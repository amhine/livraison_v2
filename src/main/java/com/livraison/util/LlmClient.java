package com.livraison.util;

public interface LlmClient {
    String callLlm(String jsonPrompt);
    java.util.List<String> extractOrderedIds(String llmResponse);
}
