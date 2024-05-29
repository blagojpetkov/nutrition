package com.nutrition.repository;



import com.nutrition.data.MealEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealEntryRepository extends JpaRepository<MealEntry, Long> {
    List<MealEntry> findByUserId(String userId);
    List<MealEntry> findByDate(LocalDate date);
}