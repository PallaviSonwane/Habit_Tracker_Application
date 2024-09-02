// src/js/app.js
document.addEventListener('DOMContentLoaded', function() {
    const habitForm = document.getElementById('add-habit-form');
    const checkInForm = document.getElementById('check-in-form');
    const habitNameInput = document.getElementById('habit-name');
    const selectHabit = document.getElementById('select-habit');
    const progressInfo = document.getElementById('progress-info');
    const reviewsInfo = document.getElementById('reviews-info');
    const quoteDiv = document.getElementById('quote');

    // Fetch and display habits
    function fetchHabits() {
        fetch('/api/habit')
            .then(response => response.json())
            .then(data => {
                selectHabit.innerHTML = '<option value="" disabled selected>Select Habit</option>';
                data.forEach(habit => {
                    const option = document.createElement('option');
                    option.value = habit.id;
                    option.textContent = habit.name;
                    selectHabit.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching habits:', error));
    }

    // Add a new habit
    habitForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const habitName = habitNameInput.value;

        fetch('/api/habit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name: habitName })
        })
        .then(response => response.json())
        .then(() => {
            habitNameInput.value = '';
            fetchHabits();  // Refresh the habit list
        })
        .catch(error => console.error('Error adding habit:', error));
    });

    // Check-in a habit
    checkInForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const habitId = selectHabit.value;

        fetch(`/api/checkin/${habitId}`, {
            method: 'POST'
        })
        .then(response => response.json())
        .then(() => {
            // Handle successful check-in
        })
        .catch(error => console.error('Error checking in habit:', error));
    });

    // Fetch progress and reviews
    function fetchProgressAndReviews() {
        fetch('/api/progress')
            .then(response => response.json())
            .then(data => {
                progressInfo.textContent = `Completion Rate: ${data.completionRate}%`;
            })
            .catch(error => console.error('Error fetching progress:', error));

        fetch('/api/reviews')
            .then(response => response.json())
            .then(data => {
                reviewsInfo.innerHTML = `<p>Daily Performance: ${data.dailyPerformance}</p><p>Weekly Performance: ${data.weeklyPerformance}</p>`;
            })
            .catch(error => console.error('Error fetching reviews:', error));
    }

    // Fetch motivational quotes
    function fetchQuote() {
        fetch('/api/quote')
            .then(response => response.json())
            .then(data => {
                quoteDiv.textContent = data.quote;
            })
            .catch(error => console.error('Error fetching quote:', error));
    }

    // Initial fetches
    fetchHabits();
    fetchProgressAndReviews();
    fetchQuote();
});
