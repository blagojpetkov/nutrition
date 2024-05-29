package com.nutrition.data;

import java.util.List;

public class NutritionalInfoResponse {
    private List<Food> foods;

    // Getters and setters
    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}