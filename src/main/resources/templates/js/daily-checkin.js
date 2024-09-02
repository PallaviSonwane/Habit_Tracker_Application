document.addEventListener('DOMContentLoaded', () => {
    const selectedDate = new Date();
    document.querySelector('#date-display').textContent = selectedDate.toLocaleString('default', { month: 'short', day: 'numeric' });

    function getDaysOfWeek(date) {
        const days = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
        const currentDay = date.getDay();
        const result = [];
        for (let i = 0; i < 7; i++) {
            const day = new Date(date);
            day.setDate(date.getDate() - currentDay + i);
            result.push({
                name: days[i],
                date: day.getDate(),
                isSelected: i === currentDay
            });
        }
        return result;
    }

    function populateCalendar() {
        const calendarDiv = document.querySelector('#calendar');
        const daysOfWeek = getDaysOfWeek(selectedDate);

        daysOfWeek.forEach(day => {
            const dayDiv = document.createElement('div');
            dayDiv.className = `date ${day.isSelected ? 'selected' : ''}`;
            dayDiv.textContent = day.date;
            calendarDiv.appendChild(dayDiv);
        });
    }

    populateCalendar();
});
