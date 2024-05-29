package com.nutrition.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class MealEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String mealDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @OneToMany(mappedBy = "mealEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Food> foods;

    private double calories;
    private double protein;
    private double fats;
    private double carbohydrates;

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        this.foods.forEach(x->{
            this.calories+=x.getNfCalories();
            this.protein+=x.getNfProtein();
            this.fats+=x.getNfTotalFat();
            this.carbohydrates+=x.getNfTotalCarbohydrate();
        });

        this.calories = Math.round(this.calories);
        this.protein = Math.round(this.protein);
        this.fats = Math.round(this.fats);
        this.carbohydrates = Math.round(this.carbohydrates);
    }
}