(() => {
    generateTimeLabels();
    generateDayLabels();
    generateTimeSlots();
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

    for (let i = 0; i < days.length; i++) {
        const tanggal = new Date(tanggalMingguIni[i]);
        const nomorHari = tanggal.getDate();   // ambil 11, 12, dst

        const label = document.createElement('div');
        label.innerHTML = `<strong>${nomorHari}</strong><br>${days[i]}`;
        container.appendChild(label);
    }
}
