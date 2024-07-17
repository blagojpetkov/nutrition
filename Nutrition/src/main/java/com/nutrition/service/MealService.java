package com.nutrition.service;


import com.nutrition.data.Food;
import com.nutrition.data.MealEntry;
import com.nutrition.repository.FoodRepository;
import com.nutrition.repository.MealEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealEntryRepository mealRepository;
    private final FoodRepository foodRepository;

    public MealService(MealEntryRepository mealRepository, FoodRepository foodRepository) {
        this.mealRepository = mealRepository;
        this.foodRepository = foodRepository;
    }


    public MealEntry saveMealEntry(MealEntry mealEntry) {
        return mealRepository.save(mealEntry);
    }

    public List<MealEntry> getMealsByUser(String userId) {
        return mealRepository.findByUserId(userId);
    }
    public List<MealEntry> getMealsByDate(LocalDate date) {
        return mealRepository.findByDate(date);
    }
    public void removeFoodFromMeal(Long mealId, Long foodId) {
        MealEntry mealEntry = mealRepository.findById(mealId).orElseThrow(() -> new RuntimeException("MealEntry not found"));
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found"));

        mealEntry.getFoods().remove(food);
        foodRepository.delete(food);
        mealRepository.save(mealEntry);
    }

}