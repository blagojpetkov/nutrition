import React, { useState } from 'react';
import axios from 'axios';
import './MealEntryForm.css';
import DatePicker from "react-datepicker";

const MealEntryForm = ({ onMealAdded }) => {
    const getCurrentDate = () => {
        const today = new Date();
        return today.toISOString().split('T')[0];
    };
    const [mealDescription, setMealDescription] = useState('');
    const [date, setDate] = useState(getCurrentDate());

    const handleDateChange = (date) => {
        setDate(date.toISOString().split('T')[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('/api/meals', {
                mealDescription,
                date
            });
            console.log('Meal entry added:', response.data);
            onMealAdded();
        } catch (error) {
            console.error('There was an error adding the meal entry!', error);
        }
    };

    return (
        <form className="meal-entry-form" onSubmit={handleSubmit}>
            <div className="input-group">
                <input
                    className={"main-meal-input"}
                    type="text"
                    id="mealDescription"
                    placeholder="Внеси оброк"
                    value={mealDescription}
                    onChange={(e) => setMealDescription(e.target.value)}
                    required
                />
                <DatePicker
                    selected={new Date(date)}
                    onChange={handleDateChange}
                    dateFormat="dd MMM, yyyy"
                    className="date-picker"
                />
            </div>
            <button type="submit">Внеси оброк</button>
        </form>
    );
};

export default MealEntryForm;
