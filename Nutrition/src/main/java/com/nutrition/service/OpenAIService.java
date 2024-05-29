package com.nutrition.service;

import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
@Service
public class OpenAIService {

    private final RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String processMealDescription(String mealDescription) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", "You are an assistant that translates and formats meal descriptions. The user will provide a description of their meals in Macedonian. Your task is to translate the description into English, then format it into a clear and structured list of food items suitable for nutritional analysis: " + mealDescription);

        JSONArray messages = new JSONArray();
        messages.put(message);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();
    }
}