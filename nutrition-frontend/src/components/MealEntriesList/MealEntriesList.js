import React, { useState } from 'react';
import axios from 'axios';
import './MealEntriesList.css';
import DatePicker from "react-datepicker";

const MealEntriesList = ({ date, mealEntries, handleFetchMeals, setDate }) => {
    const calculateMealEntryTotals = (foods) => {
        let totalCalories = 0;
        let totalProtein = 0;
        let totalFats = 0;
        let totalCarbs = 0;

        foods.forEach(food => {
            totalCalories += food.nf_calories;
            totalProtein += food.nf_protein;
            totalFats += food.nf_total_fat;
            totalCarbs += food.nf_total_carbohydrate;
        });

        return {
            totalCalories: Math.round(totalCalories),
            totalProtein: Math.round(totalProtein),
            totalFats: Math.round(totalFats),
            totalCarbs: Math.round(totalCarbs)
        };;
    };

    const calculateTotals = (entries) => {
        let totalCalories = 0;
        let totalProtein = 0;
        let totalFats = 0;
        let totalCarbs = 0;

        entries.forEach(meal => {
            const { totalCalories: mealCalories, totalProtein: mealProtein, totalFats: mealFats, totalCarbs: mealCarbs } = calculateMealEntryTotals(meal.foods);
            totalCalories += mealCalories;
            totalProtein += mealProtein;
            totalFats += mealFats;
            totalCarbs += mealCarbs;
        });

        return {
            totalCalories: Math.round(totalCalories),
            totalProtein: Math.round(totalProtein),
            totalFats: Math.round(totalFats),
            totalCarbs: Math.round(totalCarbs)
        };
    };

    const formatDisplayDate = (dateString) => {
        return new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: 'short',
            day: '2-digit'
        }).format(new Date(dateString));
    };

    const handleDateChange = (date) => {
        setDate(date.toISOString().split('T')[0]);
    };

    const handleDeleteFood = async (mealId, foodId) => {
        try {
            await axios.delete(`/api/meals/${mealId}/foods/${foodId}`);
            handleFetchMeals(); // Refresh the list after deleting a food item
        } catch (error) {
            console.error('There was an error deleting the food item!', error);
        }
    };

    const totals = calculateTotals(mealEntries);

    return (
        <div className="meal-entries-list">
            <form onSubmit={(e) => { e.preventDefault(); handleFetchMeals(); }}>
                <DatePicker
                    selected={new Date(date)}
                    onChange={handleDateChange}
                    dateFormat="dd MMM, yyyy"
                    className="date-picker"
                />
            </form>

            {mealEntries.length > 0 && (
                <div className="totals">
                    <h3>Макронутриенти за {formatDisplayDate(date)}</h3>
                    <p>Калории (kcal): {totals.totalCalories}</p>
                    <p>Протеини: {totals.totalProtein} грама</p>
                    <p>Масти: {totals.totalFats} грама</p>
                    <p>Јаглехидрати: {totals.totalCarbs} грама</p>
                </div>
            )}

            <div className="meal-entries">
                {mealEntries.map((mealEntry) => {
                    const mealTotals = calculateMealEntryTotals(mealEntry.foods);
                    return (
                        <div key={mealEntry.id} className="meal-entry">
                            <h3>ОБРОК {mealEntry.id}</h3>
                            <p>{mealEntry.mealDescription}</p>
                            <p>{mealTotals.totalCalories} калории</p>
                            <p>{mealTotals.totalProtein} грама протеини</p>
                            <p>{mealTotals.totalFats} грама масти</p>
                            <p>{mealTotals.totalCarbs} грама јаглехидрати</p>
                            <div className="food-list">
                                {mealEntry.foods.map((food) => (
                                    <div key={food.id} className="food-item">
                                        {food.food_name}: {food.serving_qty} {food.serving_unit} - {food.nf_calories} kcal
                                        <button className="delete-button" onClick={() => handleDeleteFood(mealEntry.id, food.id)}>
                                            🗑️
                                        </button>
                                    </div>
                                ))}
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default MealEntriesList;
