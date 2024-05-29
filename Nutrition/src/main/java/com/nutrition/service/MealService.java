package com.nutrition.service;


import com.nutrition.data.MealEntry;
import com.nutrition.repository.MealEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealEntryRepository mealEntryRepository;

    public MealService(MealEntryRepository mealEntryRepository) {
        this.mealEntryRepository = mealEntryRepository;
    }

    public MealEntry saveMealEntry(MealEntry mealEntry) {
        return mealEntryRepository.save(mealEntry);
    }

    public List<MealEntry> getMealsByUser(String userId) {
        return mealEntryRepository.findByUserId(userId);
    }
    public List<MealEntry> getMealsByDate(LocalDate date) {
        return mealEntryRepository.findByDate(date);
    }

}