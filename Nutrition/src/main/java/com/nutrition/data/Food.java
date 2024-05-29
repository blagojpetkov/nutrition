package com.nutrition.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_entry_id")
    @JsonBackReference
    private MealEntry mealEntry;

    @JsonProperty("food_name")
    private String foodName;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("serving_qty")
    private double servingQty;

    @JsonProperty("serving_unit")
    private String servingUnit;

    @JsonProperty("serving_weight_grams")
    private double servingWeightGrams;

    @JsonProperty("nf_calories")
    private double nfCalories;

    @JsonProperty("nf_total_fat")
    private double nfTotalFat;

    @JsonProperty("nf_saturated_fat")
    private double nfSaturatedFat;

    @JsonProperty("nf_cholesterol")
    private double nfCholesterol;

    @JsonProperty("nf_sodium")
    private double nfSodium;

    @JsonProperty("nf_total_carbohydrate")
    private double nfTotalCarbohydrate;

    @JsonProperty("nf_dietary_fiber")
    private double nfDietaryFiber;

    @JsonProperty("nf_sugars")
    private double nfSugars;

    @JsonProperty("nf_protein")
    private double nfProtein;

    @JsonProperty("nf_potassium")
    private double nfPotassium;

    @JsonProperty("nf_p")
    private double nfP;

}