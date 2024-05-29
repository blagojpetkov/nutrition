package com.nutrition.service;

import com.nutrition.data.Food;
import com.nutrition.data.NutritionalInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
@Service
public class NutritionService {

    private final RestTemplate restTemplate;

    @Value("${nutritionix.api.appId}")
    private String appId;

    @Value("${nutritionix.api.appKey}")
    private String apiKey;

    public NutritionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Food> getNutritionalInfo(String cleanedDescription) {
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-app-id", appId);
        headers.set("x-app-key", apiKey);

        HttpEntity<Object> entity = new HttpEntity<>(Collections.singletonMap("query", cleanedDescription), headers);

        NutritionalInfoResponse response = restTemplate.postForObject(url, entity, NutritionalInfoResponse.class);

        if (response != null) {
            return response.getFoods();
        } else {
            return Collections.emptyList();
        }
    }
}