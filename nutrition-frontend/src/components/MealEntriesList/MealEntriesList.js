import React, { useState } from 'react';
import axios from 'axios';
import './MealEntriesList.css';
import DatePicker from "react-datepicker";

const MealEntriesList = ({ date, mealEntries, handleFetchMeals, setDate }) => {

    const calculateTotals = () => {
        let totalCalories = 0;
        let totalProtein = 0;
        let totalFats = 0;
        let totalCarbs = 0;

        mealEntries.forEach(meal => {
            totalCalories += meal.calories;
            totalProtein += meal.protein;
            totalFats += meal.fats;
            totalCarbs += meal.carbohydrates;
        });

        return { totalCalories, totalProtein, totalFats, totalCarbs };
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

    const totals = calculateTotals();

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
                {mealEntries.map((mealEntry) => (
                    <div key={mealEntry.id} className="meal-entry">
                        <h3>ОБРОК {mealEntry.id}</h3>
                        <p>{mealEntry.mealDescription}</p>
                        <p>{mealEntry.calories} калории</p>
                        <p>{mealEntry.protein} грама протеини</p>
                        <p>{mealEntry.fats} грама масти</p>
                        <p>{mealEntry.carbohydrates} грама јаглехидрати</p>
                        <div className="food-list">
                            {mealEntry.foods.map((food) => (
                                <div key={food.id} className="food-item">
                                    {food.food_name}: {food.serving_qty} {food.serving_unit} - {food.nf_calories} calories
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default MealEntriesList;
