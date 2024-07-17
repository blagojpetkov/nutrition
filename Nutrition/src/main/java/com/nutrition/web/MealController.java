package com.nutrition.web;

import com.nutrition.data.Food;
import com.nutrition.data.MealEntry;
import com.nutrition.service.MealService;
import com.nutrition.service.NutritionService;
import com.nutrition.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    private final OpenAIService openAIService;

    private final NutritionService nutritionService;

    public MealController(MealService mealService, OpenAIService openAIService, NutritionService nutritionService) {
        this.mealService = mealService;
        this.openAIService = openAIService;
        this.nutritionService = nutritionService;
    }

    @PostMapping
    public ResponseEntity<MealEntry> addMeal(@RequestBody MealEntry mealEntry) {
        String cleanedDescription = openAIService.processMealDescription(mealEntry.getMealDescription());
        List<Food> foods = nutritionService.getNutritionalInfo(cleanedDescription);

        for (Food food : foods) {
            food.setMealEntry(mealEntry);
        }

        mealEntry.setFoods(foods);
        MealEntry savedMealEntry = mealService.saveMealEntry(mealEntry);

        return ResponseEntity.ok(savedMealEntry);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MealEntry>> getUserMeals(@PathVariable String userId) {
        List<MealEntry> meals = mealService.getMealsByUser(userId);
        return ResponseEntity.ok(meals);
    }

    @GetMapping
    public ResponseEntity<List<MealEntry>> getMealsByDate(@RequestParam LocalDate date) {
        List<MealEntry> meals = mealService.getMealsByDate(date);
        return ResponseEntity.ok(meals);
    }

    @DeleteMapping("/{mealId}/foods/{foodId}")
    public ResponseEntity<Void> deleteFoodFromMeal(@PathVariable Long mealId, @PathVariable Long foodId) {
        mealService.removeFoodFromMeal(mealId, foodId);
        return ResponseEntity.noContent().build();
    }
}
