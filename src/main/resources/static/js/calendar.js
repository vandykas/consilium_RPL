(() => {
    generateTimeLabels();
    generateDayLabels();
})();

function generateTimeLabels() {
    const container = document.querySelector('#time');
    const startHour = 7;
    const endHour = 18;

    for (let hour = startHour; hour < endHour; hour++) {
        const label = document.createElement('div');
        label.textContent = hour;
        container.appendChild(label);
    }
}

function generateDayLabels() {
    const days = ['Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat'];
    const container = document.querySelector('#calendar-header');

    for (const day of days) {
        const label = document.createElement('div');
        label.textContent = day;
        container.appendChild(label);
    }
}