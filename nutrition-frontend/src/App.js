import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './App.css';
import MealEntryForm from "./components/MealEntryForm/MealEntryForm";
import MealEntriesList from "./components/MealEntriesList/MealEntriesList";

const App = () => {
    const getCurrentDate = () => {
        const today = new Date();
        return today.toISOString().split('T')[0];
    };
    const [date, setDate] = useState(getCurrentDate());
    const [mealEntries, setMealEntries] = useState([]);

    const handleFetchMeals = async () => {
        try {
            const response = await axios.get('/api/meals', {
                params: { date }
            });
            setMealEntries(response.data);
        } catch (error) {
            console.error('There was an error fetching the meal entries!', error);
        }
    };

    useEffect(() => {
        handleFetchMeals();
    }, [date]);



    return (
        <div className="App">
            <h1>Нутриција</h1>
            <MealEntryForm onMealAdded={handleFetchMeals} />
            <MealEntriesList date={date} mealEntries={mealEntries} handleFetchMeals={handleFetchMeals} setDate={setDate} />
        </div>
    );
};

export default App;
